package com.elminster.common.thread.threadpool;

import java.util.Collection;
import java.util.concurrent.Future;

/**
 * A future with listeners.
 *
 * @param <T>
 *     The result type returned by this Future's {@code get} method
 * @author jinggu
 * @version 1.0
 */
public interface ListenableFuture<T> extends Future<T> {

  /**
   * Add future listener.
   *
   * @param listener
   *     the future listener
   */
  void addListener(FutureListener listener);

  /**
   * Get the future listeners (unmodifiable).
   *
   * @return the future listeners
   */
  Collection<FutureListener> getListeners();
}
