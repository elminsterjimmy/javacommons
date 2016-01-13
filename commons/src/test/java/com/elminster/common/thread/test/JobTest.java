package com.elminster.common.thread.test;

import org.junit.Assert;
import org.junit.Test;

import com.elminster.common.thread.IJob;
import com.elminster.common.thread.IJob.JobStatus;
import com.elminster.common.thread.IJobMonitor;
import com.elminster.common.thread.Job;

public class JobTest {

  @Test
  public void testJob() {
    IJob job = new Job(0, "test") {

      @Override
      protected JobStatus doWork(IJobMonitor monitor) {
        try {
          monitor.beginJob("test", 10);
          Thread.sleep(2000);
          monitor.done();
          return JobStatus.DONE;
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          return JobStatus.ERROR;
        }
      }
      
    };
    new Thread(job).start();
    job.cancel();
    Assert.assertTrue(job.getJobStatus() == JobStatus.CANCELLED);
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
