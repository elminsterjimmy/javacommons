package com.elminster.common.thread;

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
  public long getId();

  /**
   * Get the job name.
   * 
   * @return job name
   */
  public String getName();

  /**
   * Get the job description.
   * 
   * @return job description
   */
  public String getDescription();

  /**
   * Get the job status.
   * 
   * @return job status
   */
  public JobStatus getJobStatus();
  
  /**
   * Get the job monitor.
   * @return the job monitor
   */
  public IJobMonitor getJobMonitor();
  
  /**
   * Cancel the job.
   */
  public void cancel();
  
  /**
   * The job status.
   * 
   * @author jgu
   * @version 1.0
   */
  public static enum JobStatus {
    CREATED, RUNNING, ERROR, CANCELLED, DONE;
    
    public boolean isDone() {
      return JobStatus.DONE == this;
    }
  }
}
