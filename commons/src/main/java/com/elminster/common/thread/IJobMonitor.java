package com.elminster.common.thread;

import com.elminster.common.thread.IJob.JobStatus;

public interface IJobMonitor {

  public void beginJob(String name, int totalCount);
  
  public void subJob(String name);
  
  public void worked(int progress);
  
  public int getTotalCount();
  
  public int getProgressed();
  
  public JobStatus done();
  
  public boolean isDone();
  
  public JobStatus cancel();
  
  public boolean isCancelled();
}