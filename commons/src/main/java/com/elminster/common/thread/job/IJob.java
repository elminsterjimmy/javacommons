package com.elminster.common.thread.job;

import com.elminster.common.thread.job.IJobMonitor;

/**
 * The job interface.
 * 
 * @author jgu
 * @version 1.0
 */
public interface IJob extends Runnable {

  /**
   * Get the job id.
   * 
   * @return job id
   */
  long getId();

  /**
   * Get the job name.
   * 
   * @return job name
   */
  String getName();

  /**
   * Get the job description.
   * 
   * @return job description
   */
  String getDescription();

  /**
   * Get the job status.
   * 
   * @return job status
   */
  JobStatus getJobStatus();
  
  /**
   * Get the job monitor.
   * @return the job monitor
   */
  IJobMonitor getJobMonitor();
  
  /**
   * Cancel the job.
   */
  void cancel();
  
  /**
   * The job status.
   * 
   * @author jgu
   * @version 1.0
   */
  public static enum JobStatus {
    CREATED, RUNNING, ERROR, CANCELLED, INTERRUPTED, DONE;
    
    public boolean isDone() {
      return JobStatus.DONE == this;
    }
  }
}
