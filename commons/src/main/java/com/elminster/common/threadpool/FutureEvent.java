package com.elminster.common.threadpool;

import com.elminster.common.event.Event;

public class FutureEvent implements Event {
  
  private final String name;
  private final Object source;
  private final Object target;
  
  public FutureEvent(String name, Object source, Object target) {
    this.name = name;
    this.source = source;
    this.target = target;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Object getSource() {
    return source;
  }

  @Override
  public Object getTarget() {
    return target;
  }
}