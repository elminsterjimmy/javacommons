package com.elminster.common.thread;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The job.
 *
 * @author jgu
 * @version 1.0
 */
abstract public class Job implements IJob {

  /** the logger. */
  private static final Logger logger = LoggerFactory.getLogger(Job.class);

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
   *     the job id
   * @param name
   *     the job name
   */
  public Job(long id, String name) {
    this.id = id;
    this.name = name;
    this.status = JobStatus.CREATED;
    this.monitor = new JobMonitor();
    uncatchedExceptionHandler = new LogUncatchedExceptionHandler();
  }

  /**
   * Constructor.
   *
   * @param id
   *     the job id
   * @param name
   *     the job name
   * @param monitor
   *     the job monitor
   */
  public Job(long id, String name, IJobMonitor monitor) {
    this.id = id;
    this.name = name;
    this.status = JobStatus.CREATED;
    this.monitor = monitor;
    uncatchedExceptionHandler = new LogUncatchedExceptionHandler();
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
   *     the description to set
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
    Thread.currentThread().setName(name);
    jobStarted = System.currentTimeMillis();
    if (logger.isDebugEnabled()) {
      logger.debug(String.format("Job {%X}-{%s} started at %tc.", id, name, new Date(jobStarted)));
    }
    try {
      status = JobStatus.RUNNING;
      status = doWork(monitor);
    } catch (InterruptedException ie) {
      status = JobStatus.INTERRUPTED;
      uncatchedExceptionHandler.handleUncatchedException(ie);
    } catch (Throwable t) {
      status = JobStatus.ERROR;
      uncatchedExceptionHandler.handleUncatchedException(t);
    } finally {
      if (logger.isDebugEnabled()) {
        long finishedTs = System.currentTimeMillis();
        long delta = finishedTs - jobStarted;
        logger.debug(String.format("Job [{%X}-{%s}] finished at %tc. Time delta = [%d] ms. Status = [%s].", id, name, new Date(
            finishedTs), delta, status.toString()));
      }
    }
  }

  /**
   * Set the UncatchedExceptionHandler.
   *
   * @param handler
   *     the UncatchedExceptionHandler to set
   */
  public void setUncatchedExceptionHandler(UncatchedExceptionHandler handler) {
    uncatchedExceptionHandler = handler;
  }

  /**
   * Do the actually work.
   *
   * @param monitor
   *     the job monitor
   * @return the job status
   * @throws Throwable
   *     on error
   */
  abstract protected JobStatus doWork(IJobMonitor monitor) throws Throwable;
}
