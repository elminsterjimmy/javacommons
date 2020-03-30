package com.elminster.common.thread.job;

import com.elminster.common.thread.job.IJob.JobStatus;

/**
 * The Job monitor.
 *
 * @author jgu
 * @version 1.0
 */
public interface IJobMonitor {

  /**
   * Begin the job.
   * @param name the job name
   * @param totalCount the total count of the job
   */
  void beginJob(String name, int totalCount);

  /**
   * Start a sub job.
   * @param subJobName the sub job name
   */
  void subJob(String subJobName);

  /**
   * set worked progress.
   * @param progress the worked progress
   */
  void worked(int progress);

  /**
   * Get the total job count.
   * @return the total job count
   */
  int getTotalCount();

  /**
   * Get the progressed job count.
   * @return the progressed job count
   */
  int getProgressed();

  /**
   * Done the job.
   * @return {@link JobStatus#DONE}
   */
  JobStatus done();

  /**
   * Check if the job is done.
   * @return if the job is done
   */
  boolean isDone();

  /**
   * Cancel the job.
   * @return {@link JobStatus#CANCELLED}
   */
  JobStatus cancel();

  /**
   * Check if the job is cancelled.
   * @return if the job is cancelled
   */
  boolean isCancelled();
}