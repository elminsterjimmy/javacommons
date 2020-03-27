package com.elminster.common.threadpool;

import static com.elminster.common.threadpool.ThreadPoolConfiguration.BLOCKING_QUEUE_SIZE;
import static com.elminster.common.threadpool.ThreadPoolConfiguration.CORE_POOL_SIZE;
import static com.elminster.common.threadpool.ThreadPoolConfiguration.DAEMON_THREAD;
import static com.elminster.common.threadpool.ThreadPoolConfiguration.KEEP_ALIVE_TIME;
import static com.elminster.common.threadpool.ThreadPoolConfiguration.MAX_POOL_SIZE;
import static com.elminster.common.threadpool.ThreadPoolConfiguration.POOL_NAME;
import static com.elminster.common.threadpool.ThreadPoolConfiguration.REJECTED_POLICY;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The thread pool.
 *
 * The thread pool is intend to use separated executor and scheduler to call or schedule
 * the tasks.
 *
 * It is enhanced with {@link ThreadPoolListener} to monitor the thread pool status, and also
 * auto wrap the {@link Future} into {@link ListenableFuture} to provide more functionalities
 * for reactor pattern.
 *
 * @author jgu
 * @version 1.0
 */
final public class ThreadPool {

  /** the logger. */
  public static final Logger logger = LoggerFactory.getLogger(ThreadPool.class);
  /** monitor thread pool name prefix. */
  private static final String MONITOR_THREAD_POOL_NAME_PREFIX = "thread-pool-monitor";
  /** the singleton. */
  private static final ThreadPool defaultThreadPool = new ThreadPool();
  /** the thread pool executor. */
  protected final ExecutorService executor;
  /** the thread pool listener. */
  private final List<ThreadPoolListener> listeners;
  /** the monitor executor. */
  private final ExecutorService monitorExecutor;
  /** the monitor threads. */
  private final List<FutureMonitor> futureMonitors;
  /** the scheduled thread pool executor. Don't want to make the core thread pool too large, so use an additional scheduled thread pool for scheduled works. */
  protected final ScheduledThreadPoolExecutor scheduler;

  /**
   * Default non-param constructor for initialize the default threadpool.
   */
  private ThreadPool() {
    this(ThreadPoolConfiguration.INSTANCE);
  }

  /**
   * Create the threadpool with specified configuration.
   *
   * @param cfg
   *     the configuration
   */
  public ThreadPool(ThreadPoolConfiguration cfg) {
    String rejectedPolicy = cfg.getStringProperty(REJECTED_POLICY);
    RejectedExecutionHandler rejectHandler = null;
    try {
      Class<?> clazz = Class.forName(rejectedPolicy);
      rejectHandler = (RejectedExecutionHandler) clazz.newInstance();
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
      rejectHandler = new DefaultRejectedPolicy();
    }
    executor = new ThreadPoolExecutor(cfg.getIntegerProperty(CORE_POOL_SIZE), cfg.getIntegerProperty(MAX_POOL_SIZE), cfg.getLongProperty(KEEP_ALIVE_TIME), TimeUnit.MILLISECONDS,
        new ArrayBlockingQueue<>(cfg.getIntegerProperty(BLOCKING_QUEUE_SIZE)),
        new NamedThreadFactory(cfg.getStringProperty(POOL_NAME + "-executor"), cfg.getBooleanProperty(DAEMON_THREAD)), rejectHandler);
    scheduler = new ScheduledThreadPoolExecutor(cfg.getIntegerProperty(CORE_POOL_SIZE),
        new NamedThreadFactory(cfg.getStringProperty(POOL_NAME + "-scheduler"), cfg.getBooleanProperty(DAEMON_THREAD)), rejectHandler);
    monitorExecutor = Executors.newCachedThreadPool(new NamedThreadFactory(MONITOR_THREAD_POOL_NAME_PREFIX));
    futureMonitors = new LinkedList<>();
    listeners = new ArrayList<>();
  }

  /**
   * Constructor the threadpool with the ThreadPoolExecutor and ScheduledThreadPoolExecutor.
   *
   * @param executor
   *     the ThreadPoolExecutor to use
   */
  public ThreadPool(ScheduledThreadPoolExecutor executor) {
    this(executor, executor);
  }

  /**
   * Constructor the threadpool with the ThreadPoolExecutor and ScheduledThreadPoolExecutor.
   *
   * @param executor
   *     the ThreadPoolExecutor to use
   * @param scheduledExecutor
   *     the ScheduledThreadPoolExecutor to use
   */
  public ThreadPool(ExecutorService executor, ScheduledThreadPoolExecutor scheduledExecutor) {
    this.executor = executor;
    this.scheduler = scheduledExecutor;
    monitorExecutor = Executors.newCachedThreadPool(new NamedThreadFactory(MONITOR_THREAD_POOL_NAME_PREFIX));
    futureMonitors = new LinkedList<>();
    listeners = new ArrayList<>();
  }

  /**
   * Create a threadpool with threadpool configuration.
   *
   * @param cfg
   *     the threadpool configuration
   * @return a threadpool
   */
  public static ThreadPool createThreadPool(ThreadPoolConfiguration cfg) {
    return new ThreadPool(cfg);
  }

  /**
   * Get the thread pool.
   *
   * @return the singleton thread pool
   */
  public static ThreadPool getDefaultThreadPool() {
    return defaultThreadPool;
  }

  /**
   * Submit a callable.
   *
   * @param callable
   *     the callable
   * @return a futrue
   */
  @SuppressWarnings("unchecked")
  public <V> Future<V> submit(Callable<V> callable) {
    if (null != listeners) {
      for (ThreadPoolListener tl : listeners) {
        tl.onAcceptCallable(this, callable);
      }
    }
    Future<V> future = executor.submit(callable);
    return (Future<V>) wrap(future);
  }

  /**
   * Execute a runnable.
   *
   * @param runnable
   *     the runnable
   */
  public void execute(Runnable runnable) {
    if (null != listeners) {
      for (ThreadPoolListener tl : listeners) {
        tl.onAcceptRunnable(this, runnable);
      }
    }
    executor.execute(runnable);
  }

  /**
   * Creates and executes a one-shot action that becomes enabled after the given delay.
   *
   * @param command
   *     the task to execute
   * @param delay
   *     the time from now to delay execution
   * @param unit
   *     the time unit of the delay parameter
   * @return a ScheduledFuture representing pending completion of the task and whose <tt>get()</tt> method will return <tt>null</tt> upon completion
   * @throws RejectedExecutionException
   *     if the task cannot be scheduled for execution
   * @throws NullPointerException
   *     if command is null
   */
  public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
    if (null != listeners) {
      for (ThreadPoolListener tl : listeners) {
        tl.onScheduleRunnable(this, command, delay, unit);
      }
    }
    ScheduledFuture<?> future = scheduler.schedule(command, delay, unit);
    return (ScheduledFuture<?>) wrap(future);
  }

  /**
   * Creates and executes a ScheduledFuture that becomes enabled after the given delay.
   *
   * @param callable
   *     the function to execute
   * @param delay
   *     the time from now to delay execution
   * @param unit
   *     the time unit of the delay parameter
   * @return a ScheduledFuture that can be used to extract result or cancel
   * @throws RejectedExecutionException
   *     if the task cannot be scheduled for execution
   * @throws NullPointerException
   *     if callable is null
   */
  @SuppressWarnings("unchecked")
  public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
    if (null != listeners) {
      for (ThreadPoolListener tl : listeners) {
        tl.onScheduleCallable(this, callable, delay, unit);
      }
    }
    ScheduledFuture<V> future = scheduler.schedule(callable, delay, unit);
    return (ScheduledFuture<V>) wrap(future);
  }

  /**
   * Creates and executes a periodic action that becomes enabled first after the given initial delay, and subsequently with the given period; that is executions will commence after
   * <tt>initialDelay</tt> then <tt>initialDelay+period</tt>, then <tt>initialDelay + 2 * period</tt>, and so on. If any execution of the task encounters an exception, subsequent
   * executions are suppressed. Otherwise, the task will only terminate via cancellation or termination of the executor. If any execution of this task takes longer than its period,
   * then subsequent executions may start late, but will not concurrently execute.
   *
   * @param command
   *     the task to execute
   * @param initialDelay
   *     the time to delay first execution
   * @param period
   *     the period between successive executions
   * @param unit
   *     the time unit of the initialDelay and period parameters
   * @return a ScheduledFuture representing pending completion of the task, and whose <tt>get()</tt> method will throw an exception upon cancellation
   * @throws RejectedExecutionException
   *     if the task cannot be scheduled for execution
   * @throws NullPointerException
   *     if command is null
   * @throws IllegalArgumentException
   *     if period less than or equal to zero
   */
  public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
    if (null != listeners) {
      for (ThreadPoolListener tl : listeners) {
        tl.onSchedulePeriodicRunnable(this, command, initialDelay, period, unit, true);
      }
    }
    ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(command, initialDelay, period, unit);
    return (ScheduledFuture<?>) wrap(future);
  }

  /**
   * Creates and executes a periodic action that becomes enabled first after the given initial delay, and subsequently with the given delay between the termination of one execution
   * and the commencement of the next. If any execution of the task encounters an exception, subsequent executions are suppressed. Otherwise, the task will only terminate via
   * cancellation or termination of the executor.
   *
   * @param command
   *     the task to execute
   * @param initialDelay
   *     the time to delay first execution
   * @param delay
   *     the delay between the termination of one execution and the commencement of the next
   * @param unit
   *     the time unit of the initialDelay and delay parameters
   * @return a ScheduledFuture representing pending completion of the task, and whose <tt>get()</tt> method will throw an exception upon cancellation
   * @throws RejectedExecutionException
   *     if the task cannot be scheduled for execution
   * @throws NullPointerException
   *     if command is null
   * @throws IllegalArgumentException
   *     if delay less than or equal to zero
   */
  public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
    if (null != listeners) {
      for (ThreadPoolListener tl : listeners) {
        tl.onSchedulePeriodicRunnable(this, command, initialDelay, delay, unit, false);
      }
    }
    ScheduledFuture<?> future = scheduler.scheduleWithFixedDelay(command, initialDelay, delay, unit);
    return (ScheduledFuture<?>) wrap(future);
  }

  /**
   * Shutdown the pool.
   */
  public void shutdown() {
    for (ThreadPoolListener listener : listeners) {
      listener.onShutdown(this);
    }
    executor.shutdown();
    // in case of the executor is same as the scheduler
    if (executor != scheduler) {
      scheduler.shutdown();
    }
    monitorExecutor.shutdown();
  }

  /**
   * Add thread pool listener.
   *
   * @param listener
   *     the thread pool listener
   */
  public void addThreadPoolListener(ThreadPoolListener listener) {
    this.listeners.add(listener);
  }

  /**
   * Get the thread pool executor.
   *
   * @return the thread pool executor
   */
  public ExecutorService getExecutor() {
    return executor;
  }

  /**
   * Get the thread pool scheduler.
   *
   * @return the thread pool scheduler
   */
  public ScheduledThreadPoolExecutor getScheduler() {
    return scheduler;
  }

  /**
   * Wrap the future to add monitor.
   *
   * @param future
   *     the future to wrap
   * @return wrapped future
   */
  private Future<?> wrap(Future<?> future) {
    ListenableFuture<?> listenableFuture;
    if (future instanceof ListenableFuture) {
      listenableFuture = (ListenableFuture<?>) future;
    } else {
      listenableFuture = new ListenableFutureImpl<>(future);
    }
    FutureMonitor futureMonitor = null;
    for (FutureMonitor fm : futureMonitors) {
      try {
        fm.addFuture(listenableFuture);
        futureMonitor = fm;
        break;
      } catch (FutureOverflowException e) {
        ;
      }
    }
    if (null == futureMonitor) {
      logger.debug("All future monitor are full, create new one.");
      futureMonitor = new FutureMonitor(100);
      futureMonitors.add(futureMonitor);
      try {
        futureMonitor.addFuture(listenableFuture);
        this.monitorExecutor.execute(futureMonitor);
      } catch (FutureOverflowException e) {
        logger.error("SHOULD NEVER HAPPEN!");
      }
    }
    return listenableFuture;
  }
}