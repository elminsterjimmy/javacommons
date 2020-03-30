package com.elminster.common.thread.threadpool;

public class FutureExceptionEvent extends FutureEvent {

  public FutureExceptionEvent(Object source, Object target) {
    super("exception", source, target);
  }
}
