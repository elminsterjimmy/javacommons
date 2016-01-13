package com.elminster.common.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.elminster.common.thread.IJob.JobStatus;

/**
 * The default job monitor.
 * 
 * @author jgu
 * @version 1.0
 */
public class JobMonitor implements IJobMonitor {
  
  /** the logger. */
  private static final Log logger = LogFactory.getLog(JobMonitor.class);
  
  /** the name. */
  private String name;
  
  /** the job total count. */
  private int totalCount;

  /** the job progress. */
  private int progressed;
  
  /** the job is done? */
  private volatile boolean done;
  
  /** the job is cancelled? */
  private volatile boolean cancelled;
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void beginJob(String name, int totalCount) {
    this.name = name;
    this.totalCount = totalCount;
    if (logger.isDebugEnabled()) {
      logger.debug(String.format("Begin job [%s] with total [%d].", name, totalCount));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void subJob(String name) {
    if (logger.isDebugEnabled()) {
      logger.debug(String.format("Begin sub job [%s].", name));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void worked(int progress) {
    this.progressed += progress;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JobStatus done() {
    done = true;
    if (logger.isDebugEnabled()) {
      logger.debug(String.format("Job [%s] is done.", name));
    }
    return JobStatus.DONE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDone() {
    return done;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JobStatus cancel() {
    cancelled = true;
    if (logger.isDebugEnabled()) {
      logger.debug(String.format("Job [%s] is cancelled.", name));
    }
    return JobStatus.CANCELLED;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isCancelled() {
    return cancelled;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getTotalCount() {
    return totalCount;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getProgressed() {
    return progressed;
  }

}
