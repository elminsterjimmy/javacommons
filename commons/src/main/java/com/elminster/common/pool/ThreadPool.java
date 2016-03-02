package com.elminster.common.pool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.elminster.common.config.CommonConfiguration;
import com.elminster.common.config.IConfigProvider;

/**
 * The thread pool.
 * 
 * @author jgu
 * @version 1.0
 */
final public class ThreadPool {
  /** the thread pool configuration file name. */
  private static final String CONIGURATION_FILE = "threadpool.properties";
  /** the thread pool core size. */
  public static final String CORE_POOL_SIZE = "threadpool.core.size";
  /** the thread pool max size. */
  public static final String MAX_POOL_SIZE = "threadpool.max.size";
  /** the thread pool keep alive time (unit ms). */
  public static final String KEEP_ALIVE_TIME = "threadpool.keep.alive.time";
  /** the logger. */
  public static final Log logger = LogFactory.getLog(ThreadPool.class);
  /** the singleton. */
  private static ThreadPool pool = new ThreadPool();
  /** the thread pool executor. */
  protected ThreadPoolExecutor executor;
  /** the thread pool listener. */
  private List<ThreadPoolListener> listeners;
  
  /**
   * the scheduled thread pool executor. Don't want to make the core thread pool too large, so use an additional
   * scheduled thread pool for scheduled works.
   */
  protected ScheduledThreadPoolExecutor scheduledExecutor;

  /**
   * Singleton
   */
  private ThreadPool() {
    IConfigProvider conf = new CommonConfiguration() {
      protected void loadResources() {
        File configFile = new File(CONIGURATION_FILE);
        if (configFile.exists()) {
          InputStream is = null;
          try {
            is = new FileInputStream(configFile);
            properties.load(is);
          } catch (IOException e) {
            if (logger.isDebugEnabled()) {
              logger.debug(e);
            }
          } finally {
            if (null != is) {
              try {
                is.close();
              } catch (IOException e) {
                if (logger.isDebugEnabled()) {
                  logger.debug(e);
                }
              }
            }
          }
        }
      }
    };
    quickInit(conf);
  }

  /**
   * Initialize the pool.
   * 
   * @param cfg
   *          the config
   */
  private void quickInit(IConfigProvider cfg) {
    executor = new ThreadPoolExecutor(cfg.getIntegerProperty(CORE_POOL_SIZE, 10), cfg.getIntegerProperty(MAX_POOL_SIZE,
        10), cfg.getLongProperty(KEEP_ALIVE_TIME, 0L), TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(
        cfg.getIntegerProperty(CORE_POOL_SIZE, 10)));
    scheduledExecutor = new ScheduledThreadPoolExecutor(cfg.getIntegerProperty(CORE_POOL_SIZE, 10));
    listeners = new ArrayList<ThreadPoolListener>();
  }

  /**
   * Get the thread pool.
   * 
   * @return the singleton thread pool
   */
  public static ThreadPool getThreadPool() {
    return pool;
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
   * @return a ScheduledFuture representing pending completion of the task and whose <tt>get()</tt> method will return
   *         <tt>null</tt> upon completion
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
   * Creates and executes a periodic action that becomes enabled first after the given initial delay, and subsequently
   * with the given period; that is executions will commence after <tt>initialDelay</tt> then
   * <tt>initialDelay+period</tt>, then <tt>initialDelay + 2 * period</tt>, and so on. If any execution of the task
   * encounters an exception, subsequent executions are suppressed. Otherwise, the task will only terminate via
   * cancellation or termination of the executor. If any execution of this task takes longer than its period, then
   * subsequent executions may start late, but will not concurrently execute.
   *
   * @param command
   *          the task to execute
   * @param initialDelay
   *          the time to delay first execution
   * @param period
   *          the period between successive executions
   * @param unit
   *          the time unit of the initialDelay and period parameters
   * @return a ScheduledFuture representing pending completion of the task, and whose <tt>get()</tt> method will throw
   *         an exception upon cancellation
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
   * Creates and executes a periodic action that becomes enabled first after the given initial delay, and subsequently
   * with the given delay between the termination of one execution and the commencement of the next. If any execution of
   * the task encounters an exception, subsequent executions are suppressed. Otherwise, the task will only terminate via
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
   * @return a ScheduledFuture representing pending completion of the task, and whose <tt>get()</tt> method will throw
   *         an exception upon cancellation
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
   * @param listener the thread pool listener
   */
  public void addThreadPoolListener(ThreadPoolListener listener) {
    this.listeners.add(listener);
  }
}