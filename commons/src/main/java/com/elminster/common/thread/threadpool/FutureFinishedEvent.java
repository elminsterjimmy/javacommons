package com.elminster.common.thread.threadpool;

public class FutureFinishedEvent extends FutureEvent {

  public FutureFinishedEvent(Object source, Object target) {
    super("finished", source, target);
  }
}
