package com.elminster.common.thread.test;

import org.junit.Assert;
import org.junit.Test;

import com.elminster.common.thread.job.IJob;
import com.elminster.common.thread.job.IJob.JobStatus;
import com.elminster.common.thread.job.IJobMonitor;
import com.elminster.common.thread.job.Job;

public class JobTest {

  @Test
  public void testJob() {
    IJob job = new Job(0, "test") {

      @Override
      protected JobStatus doWork(IJobMonitor monitor) throws InterruptedException {
        monitor.beginJob("Test Job", 10);
        int worked = 0;
        while (!monitor.isCancelled()) {
          Thread.sleep(300);
          monitor.subJob("Test SubJob " + worked);
          monitor.worked(++worked);
        }
        monitor.done();
        return JobStatus.DONE;
      }
      
    };
    new Thread(job).start();
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    job.cancel();
    Assert.assertTrue(job.getJobStatus() == JobStatus.CANCELLED);
  }
}
