package com.elminster.common.threadpool;

import com.elminster.common.listener.Listener;

/**
 * The future listener.
 * 
 * @author jinggu
 * @version 1.0
 */
public interface FutureListener extends Listener {

  /**
   * Callback on future done.
   */
  public void onFinished(FutureEvent event);

  /**
   * Callback on future throws exception.
   */
  public void onException(FutureEvent event);

  /**
   * Callback on future is cancelled.
   */
  public void onCancelled(FutureEvent event);
}