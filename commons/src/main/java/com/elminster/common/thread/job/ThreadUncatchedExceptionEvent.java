package com.elminster.common.thread.job;

import com.elminster.common.util.ExceptionUtil;

public class ThreadUncatchedExceptionEvent {

  private final Throwable throwable;
  private final Job job;

  public ThreadUncatchedExceptionEvent(final Throwable throwable, Job job) {
    this.throwable = throwable;
    this.job = job;
  }

  public Throwable getThrowable() {
    return throwable;
  }

  public Job getJob() {
    return job;
  }

  public String toString() {
    return new StringBuilder().append("Job [").append(job.getName()).append("] thrown Exception :").append(ExceptionUtil.getStackTrace(throwable)).toString();
  }
}
