package com.elminster.common.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

  private static ThreadPool pool = new ThreadPool();

  private ThreadPoolExecutor executor;
  private boolean initialized = false;

  /**
   * Singleton
   */
  private ThreadPool() {
  }

  public void quickInit() {
    executor = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10));
    initialized = true;
  }

  public void quickInit(int coreSize) {
    executor = new ThreadPoolExecutor(coreSize, coreSize * 2, 0L, TimeUnit.MILLISECONDS,
        new ArrayBlockingQueue<Runnable>(coreSize * 2));
    initialized = true;
  }

  public void init(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
      BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
    executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory,
        handler);
    initialized = true;
  }

  public static ThreadPool getThreadPool() {
    return pool;
  }

  private void checkInitialized() throws IllegalStateException {
    if (!initialized) {
      throw new IllegalStateException("The Thread Pool hasn't been initialized.");
    }
  }

  public Future<?> submit(Callable<?> callable) {
    checkInitialized();
    return executor.submit(callable);
  }

  public void execute(Runnable runnable) {
    checkInitialized();
    executor.execute(runnable);
  }

  public void shutdown() {
    checkInitialized();
    executor.shutdown();
  }
}