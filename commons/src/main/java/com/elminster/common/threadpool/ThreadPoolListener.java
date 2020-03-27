package com.elminster.common.threadpool;

import com.elminster.common.listener.Listener;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * The thread pool listener.
 *
 * @author jgu
 * @version 1.0
 */
public interface ThreadPoolListener extends Listener {

  /**
   * On thread pool shutdown.
   */
  void onShutdown(ThreadPool threadPool);

  /**
   * On thread pool accept a {@link Runnable}.
   */
  void onAcceptRunnable(ThreadPool threadPool, Runnable runnable);

  /**
   * On thread pool accept a {@link Callable}.
   */
  <V> void onAcceptCallable(ThreadPool threadPool, Callable<V> callable);

  /**
   * On thread pool schedule a {@link Runnable}.
   */
  void onScheduleRunnable(ThreadPool threadPool, Runnable runnable, long delay, TimeUnit unit);

  /**
   * On thread pool schedule a {@link Callable}.
   */
  <V> void onScheduleCallable(ThreadPool threadPool, Callable<V> callable, long delay, TimeUnit unit);

  /**
   * On thread pool schedule a periodic {@link Runnable}.
   */
  void onSchedulePeriodicRunnable(ThreadPool threadPool, Runnable runnable, long initialDelay, long period, TimeUnit unit, boolean atFixRate);
}
