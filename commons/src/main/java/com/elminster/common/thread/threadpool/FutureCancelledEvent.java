package com.elminster.common.thread.threadpool;

public class FutureCancelledEvent extends FutureEvent {

  public FutureCancelledEvent(Object source, Object target) {
    super("cancelled", source, target);
  }
}
