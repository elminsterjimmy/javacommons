package com.elminster.common.thread;

import com.elminster.common.thread.IJob.JobStatus;

public interface IJobMonitor {

  void beginJob(String name, int totalCount);
  
  void subJob(String subJobName);
  
  void worked(int progress);
  
  int getTotalCount();
  
  int getProgressed();
  
  JobStatus done();
  
  boolean isDone();
  
  JobStatus cancel();
  
  boolean isCancelled();
}