package com.elminster.common.thread.job;

import java.util.Observable;

public class SaveAndNotifyUncatchedExceptionHandler extends Observable implements UncatchedExceptionHandler {

  private final Job job;
  
  public SaveAndNotifyUncatchedExceptionHandler(Job job) {
    this.job = job;
  }

  @Override
  public void handleUncatchedException(Throwable t) {
    ThreadUncatchedExceptionEvent event = new ThreadUncatchedExceptionEvent(t, job);
    notifyObservers(event);
  }
}
