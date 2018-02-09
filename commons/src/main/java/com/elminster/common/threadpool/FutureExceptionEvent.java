package com.elminster.common.threadpool;

public class FutureExceptionEvent extends FutureEvent {

  public FutureExceptionEvent(Object source, Object target) {
    super("exception", source, target);
  }
}
