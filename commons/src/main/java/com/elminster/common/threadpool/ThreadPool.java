package com.elminster.common.threadpool;

import static com.elminster.common.threadpool.ThreadPoolConfiguration.CORE_POOL_SIZE;
import static com.elminster.common.threadpool.ThreadPoolConfiguration.DAEMON_THREAD;
import static com.elminster.common.threadpool.ThreadPoolConfiguration.KEEP_ALIVE_TIME;
import static com.elminster.common.threadpool.ThreadPoolConfiguration.MAX_POOL_SIZE;
import static com.elminster.common.threadpool.ThreadPoolConfiguration.POOL_NAME;
import static com.elminster.common.threadpool.ThreadPoolConfiguration.REJECTED_POLICY;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The thread pool.
 * 
 * @author jgu
 * @version 1.0
 */
final public class ThreadPool {

  /** the logger. */
  public static final Log logger = LogFactory.getLog(ThreadPool.class);
  /** the singleton. */
  private static final ThreadPool defaultThreadPool = new ThreadPool();
  /** the thread pool executor. */
  protected final ThreadPoolExecutor executor;
  /** the thread pool listener. */
  private final List<ThreadPoolListener> listeners;

  /**
   * the scheduled thread pool executor. Don't want to make the core thread pool too large, so use an additional scheduled thread pool for scheduled works.
   */
  protected final ScheduledThreadPoolExecutor scheduledExecutor;

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
   *          the configuration
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
        new ArrayBlockingQueue<Runnable>(cfg.getIntegerProperty(CORE_POOL_SIZE)), new NamedThreadFactory(cfg.getStringProperty(POOL_NAME), cfg.getBooleanProperty(DAEMON_THREAD)),
        rejectHandler);
    scheduledExecutor = new ScheduledThreadPoolExecutor(cfg.getIntegerProperty(CORE_POOL_SIZE),
        new NamedThreadFactory(cfg.getStringProperty(POOL_NAME), cfg.getBooleanProperty(DAEMON_THREAD)), rejectHandler);
    listeners = new ArrayList<ThreadPoolListener>();
  }

  /**
   * Create a threadpool with threadpool configuration.
   * 
   * @param cfg
   *          the threadpool configuration
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
   *          the callable
   * @return a futrue
   */
  public Future<?> submit(Callable<?> callable) {
    return executor.submit(callable);
  }

  /**
   * Execute a runnable.
   * 
   * @param runnable
   *          the runnable
   */
  public void execute(Runnable runnable) {
    executor.execute(runnable);
  }

  /**
   * Creates and executes a one-shot action that becomes enabled after the given delay.
   *
   * @param command
   *          the task to execute
   * @param delay
   *          the time from now to delay execution
   * @param unit
   *          the time unit of the delay parameter
   * @return a ScheduledFuture representing pending completion of the task and whose <tt>get()</tt> method will return <tt>null</tt> upon completion
   * @throws RejectedExecutionException
   *           if the task cannot be scheduled for execution
   * @throws NullPointerException
   *           if command is null
   */
  public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
    return scheduledExecutor.schedule(command, delay, unit);
  }

  /**
   * Creates and executes a ScheduledFuture that becomes enabled after the given delay.
   *
   * @param callable
   *          the function to execute
   * @param delay
   *          the time from now to delay execution
   * @param unit
   *          the time unit of the delay parameter
   * @return a ScheduledFuture that can be used to extract result or cancel
   * @throws RejectedExecutionException
   *           if the task cannot be scheduled for execution
   * @throws NullPointerException
   *           if callable is null
   */
  public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
    return scheduledExecutor.schedule(callable, delay, unit);
  }

  /**
   * Creates and executes a periodic action that becomes enabled first after the given initial delay, and subsequently with the given period; that is executions will commence after
   * <tt>initialDelay</tt> then <tt>initialDelay+period</tt>, then <tt>initialDelay + 2 * period</tt>, and so on. If any execution of the task encounters an exception, subsequent
   * executions are suppressed. Otherwise, the task will only terminate via cancellation or termination of the executor. If any execution of this task takes longer than its period,
   * then subsequent executions may start late, but will not concurrently execute.
   *
   * @param command
   *          the task to execute
   * @param initialDelay
   *          the time to delay first execution
   * @param period
   *          the period between successive executions
   * @param unit
   *          the time unit of the initialDelay and period parameters
   * @return a ScheduledFuture representing pending completion of the task, and whose <tt>get()</tt> method will throw an exception upon cancellation
   * @throws RejectedExecutionException
   *           if the task cannot be scheduled for execution
   * @throws NullPointerException
   *           if command is null
   * @throws IllegalArgumentException
   *           if period less than or equal to zero
   */
  public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
    return scheduledExecutor.scheduleAtFixedRate(command, initialDelay, period, unit);
  }

  /**
   * Creates and executes a periodic action that becomes enabled first after the given initial delay, and subsequently with the given delay between the termination of one execution
   * and the commencement of the next. If any execution of the task encounters an exception, subsequent executions are suppressed. Otherwise, the task will only terminate via
   * cancellation or termination of the executor.
   *
   * @param command
   *          the task to execute
   * @param initialDelay
   *          the time to delay first execution
   * @param delay
   *          the delay between the termination of one execution and the commencement of the next
   * @param unit
   *          the time unit of the initialDelay and delay parameters
   * @return a ScheduledFuture representing pending completion of the task, and whose <tt>get()</tt> method will throw an exception upon cancellation
   * @throws RejectedExecutionException
   *           if the task cannot be scheduled for execution
   * @throws NullPointerException
   *           if command is null
   * @throws IllegalArgumentException
   *           if delay less than or equal to zero
   */
  public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
    return scheduledExecutor.scheduleWithFixedDelay(command, initialDelay, delay, unit);
  }

  /**
   * Shutdown the pool.
   */
  public void shutdown() {
    for (ThreadPoolListener listener : listeners) {
      listener.onShutdown();
    }
    executor.shutdown();
    scheduledExecutor.shutdown();
  }

  /**
   * Add thread pool listener.
   * 
   * @param listener
   *          the thread pool listener
   */
  public void addThreadPoolListener(ThreadPoolListener listener) {
    this.listeners.add(listener);
  }

  /**
   * Get the thread pool executor.
   * 
   * @return the thread pool executor
   */
  public ThreadPoolExecutor getExecutor() {
    return executor;
  }
}