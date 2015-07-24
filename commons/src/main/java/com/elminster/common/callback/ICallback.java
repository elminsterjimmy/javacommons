package com.elminster.common.callback;

/**
 * The callback interface.
 * @author jgu
 * @version 1.0
 * @param <T> notification type
 */
public interface ICallback<T> {

  /**
   * Callback with the notification.
   * @param notification the nofification
   */
  public void callback(T notification);
}
