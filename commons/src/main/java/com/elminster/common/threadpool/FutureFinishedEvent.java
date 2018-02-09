package com.elminster.common.threadpool;

public class FutureFinishedEvent extends FutureEvent {

  public FutureFinishedEvent(Object source, Object target) {
    super("finished", source, target);
  }
}
