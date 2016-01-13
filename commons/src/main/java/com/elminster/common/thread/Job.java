package com.elminster.common.thread;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The job.
 * 
 * @author jgu
 * @version 1.0
 */
abstract public class Job implements IJob {

  /** the logger. */
  private static final Log logger = LogFactory.getLog(Job.class);

  /** the job id. */
  private final long id;

  /** the job name. */
  private final String name;

  /** the job description. */
  private String description;

  /** the job status. */
  private volatile JobStatus status;
  
  /** the job monitor. */
  private final IJobMonitor monitor;
  
  /** the job started in ms. */
  private long jobStarted;
  
  /** the uncatched exception handler. */
  private UncatchedExceptionHandler uncatchedExceptionHandler;
  
  /**
   * Constructor.
   * 
   * @param id
   *          the job id
   * @param name
   *          the job name
   */
  public Job(long id, String name) {
    this.id = id;
    this.name = name;
    this.status = JobStatus.CREATED;
    this.monitor = new JobMonitor();
    uncatchedExceptionHandler = new NoneUncatchedExceptionHandler();
  }
  
  /**
   * Constructor.
   * 
   * @param id
   *          the job id
   * @param name
   *          the job name
   * @param monitor
   *          the job monitor
   */
  public Job(long id, String name, IJobMonitor monitor) {
    this.id = id;
    this.name = name;
    this.status = JobStatus.CREATED;
    this.monitor = monitor;
    uncatchedExceptionHandler = new NoneUncatchedExceptionHandler();
  }

  /**
   * {@inheritDoc}
   */
  public long getId() {
    return id;
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {
    return name;
  }

  /**
   * {@inheritDoc}
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description
   *          the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JobStatus getJobStatus() {
    return status;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IJobMonitor getJobMonitor() {
    return monitor;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void cancel() {
    monitor.cancel();
    status = JobStatus.CANCELLED;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {
    jobStarted = System.currentTimeMillis();
    if (logger.isDebugEnabled()) {
      logger.debug(String.format("Job %s [%d] started at %tc.", name, id, new Date(jobStarted)));
    }
    try {
      status = JobStatus.RUNNING;
      status = doWork(monitor);
    } catch (Throwable t) {
      status = JobStatus.ERROR;
      uncatchedExceptionHandler.handleUncatchedException(t);
      monitor.cancel();
    } finally {
      if (logger.isDebugEnabled()) {
        long finishedTs = System.currentTimeMillis();
        long delta = finishedTs - jobStarted;
        logger.debug(String.format("Job %s [%d] finished at %tc. Time delta = %dms. Status = %s", name, id, new Date(
            finishedTs), delta, status.toString()));
      }
    }
  }
  
  protected void setUncatchedExceptionHandler(UncatchedExceptionHandler handler) {
    uncatchedExceptionHandler = handler;
  }

  abstract protected JobStatus doWork(IJobMonitor monitor);
}
