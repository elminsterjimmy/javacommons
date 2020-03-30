package com.elminster.common.thread.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class ThreadPoolAdapter implements ThreadPoolListener {

  @Override
  public void onShutdown(ThreadPool threadPool) {

  }

  @Override
  public void onAcceptRunnable(ThreadPool threadPool, Runnable runnable) {

  }

  @Override
  public <V> void onAcceptCallable(ThreadPool threadPool, Callable<V> callable) {

  }

  @Override
  public void onScheduleRunnable(ThreadPool threadPool, Runnable runnable, long delay, TimeUnit unit) {

  }

  @Override
  public <V> void onScheduleCallable(ThreadPool threadPool, Callable<V> callable, long delay, TimeUnit unit) {

  }

  @Override
  public void onSchedulePeriodicRunnable(ThreadPool threadPool, Runnable runnable, long initialDelay, long period, TimeUnit unit, boolean atFixRate) {

  }
}
