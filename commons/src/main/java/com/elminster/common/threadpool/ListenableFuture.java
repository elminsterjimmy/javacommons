package com.elminster.common.threadpool;

import java.util.Collection;
import java.util.concurrent.Future;

/**
 * A future with listeners.
 * 
 * @author jinggu
 * @version 1.0
 * 
 * @param <T>
 *          The result type returned by this Future's {@code get} method
 */
public interface ListenableFuture<T> extends Future<T> {

  /**
   * Add future listener.
   * 
   * @param listener
   *          the future listener
   */
  public void addListener(FutureListener listener);

  /**
   * Get the future listeners (unmodifiable).
   * 
   * @return the future listeners
   */
  public Collection<FutureListener> getListeners();
}
