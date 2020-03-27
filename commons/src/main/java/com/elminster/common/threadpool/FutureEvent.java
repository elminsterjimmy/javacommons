package com.elminster.common.threadpool;

import com.elminster.common.event.Event;

/**
 * The Future Event.
 *
 * @author jgu
 * @version 1.0
 */
public class FutureEvent implements Event {
  
  private final String name;
  private final Object source;
  private final Object target;
  private final long when;
  
  public FutureEvent(String name, Object source, Object target) {
    this.name = name;
    this.source = source;
    this.target = target;
    this.when = System.currentTimeMillis();
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

  @Override
  public long getWhen() {
    return when;
  }
}