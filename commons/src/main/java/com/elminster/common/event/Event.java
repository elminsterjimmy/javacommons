package com.elminster.common.event;

/**
 * The event interface.
 * 
 * @author jinggu
 * @version 1.0
 */
public interface Event {

  /**
   * Get the event name.
   * @return the event name
   */
  String getName();
  
  /**
   * Get the event source.
   * @return the event source
   */
  Object getSource();
  
  /**
   * Get the event target.
   * @return the event target
   */
  Object getTarget();

  /**
   * Get when the event happens.
   * @return when the event happens
   */
  long getWhen();
}
