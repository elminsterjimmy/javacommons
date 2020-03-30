package com.elminster.common.thread.wrapper;

import com.elminster.common.thread.threadpool.ThreadPool;
import com.elminster.common.util.Assert;

import java.util.concurrent.*;

/**
 * The timeout wrapper for both {@link Runnable} and {@link Callable}.
 * Notice that, after timeout, it will only try to interrupt the thread,
 * if the main logic does not handle the interruption, the logic may run
 * continuely.
 *
 * @author jgu
 * @version 1.0
 */
public class TimeoutWrapper {

  /**
   * The wrapper of run with timeout in version {@link Runnable}.
   *
   * @param runnable
   *     the runnable to run
   * @param threadPool
   *     the thread pool to run on
   * @param timeout
   *     the timeout in ms
   * @throws InterruptedException
   *     on interrupted ex
   * @throws ExecutionException
   *     on execution ex
   * @throws TimeoutException
   *     on timeout ex
   */
  public static void runWithTimeout(Runnable runnable, ThreadPool threadPool, long timeout) throws InterruptedException, ExecutionException, TimeoutException {
    Assert.notNull(runnable);
    FutureTask<Void> futureTask = new FutureTask<>(runnable, null);
    runWithTimeout(futureTask, threadPool, timeout, TimeUnit.MILLISECONDS);
  }

  /**
   * The wrapper of run with timeout in version {@link Runnable}.
   *
   * @param runnable
   *     the runnable to run
   * @param threadPool
   *     the thread pool to run on
   * @param timeout
   *     the timeout
   * @param timeUnit
   *     the timeout unit
   * @throws InterruptedException
   *     on interrupted ex
   * @throws ExecutionException
   *     on execution ex
   * @throws TimeoutException
   *     on timeout ex
   */
  public static void runWithTimeout(Runnable runnable, ThreadPool threadPool, long timeout, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
    Assert.notNull(runnable);
    FutureTask<Void> futureTask = new FutureTask<>(runnable, null);
    runWithTimeout(futureTask, threadPool, timeout, timeUnit);
  }

  /**
   * The wrapper of run with timeout in version {@link Callable}.
   *
   * @param callable
   *     the runnable to run
   * @param threadPool
   *     the thread pool to run on
   * @param timeout
   *     the timeout in ms
   * @throws InterruptedException
   *     on interrupted ex
   * @throws ExecutionException
   *     on execution ex
   * @throws TimeoutException
   *     on timeout ex
   */
  public static <V> V runWithTimeout(Callable<V> callable, ThreadPool threadPool, long timeout) throws TimeoutException, ExecutionException, InterruptedException {
    return runWithTimeout(callable, threadPool, timeout, TimeUnit.MILLISECONDS);
  }

  /**
   * The wrapper of run with timeout in version {@link Callable}.
   *
   * @param callable
   *     the runnable to run
   * @param threadPool
   *     the thread pool to run on
   * @param timeout
   *     the timeout
   * @param timeUnit
   *     the timeout unit
   * @throws InterruptedException
   *     on interrupted ex
   * @throws ExecutionException
   *     on execution ex
   * @throws TimeoutException
   *     on timeout ex
   */
  public static <V> V runWithTimeout(Callable<V> callable, ThreadPool threadPool, long timeout, TimeUnit timeUnit) throws TimeoutException, ExecutionException, InterruptedException {
    Assert.notNull(callable);
    FutureTask<V> futureTask = new FutureTask<>(callable);
    return runWithTimeout(futureTask, threadPool, timeout, timeUnit);
  }

  private static <V> V runWithTimeout(FutureTask<V> futureTask, ThreadPool threadPool, long timeout, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
    Future<V> future = threadPool.submit(futureTask);
    try {
      return future.get(timeout, timeUnit);
    } catch (TimeoutException timeoutEx) {
      future.cancel(true);
      throw timeoutEx;
    }
  }
}
