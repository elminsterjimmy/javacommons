package com.elminster.common.threadpool;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default JVM shutdown hook for Thread Pool.
 * The hook will first reject all new task sent to the thread pool, and then try to wait specified
 * time to terminate the actived threads.
 * 
 * @author jgu
 * @version 1.0
 */
public class ThreadPoolJVMShutdownHook extends Thread {
  
  /** the logger. */
  private static final Logger logger = LoggerFactory.getLogger(ThreadPoolJVMShutdownHook.class);
  /** the timeout. */
  private long timeout;
  /** the time unit for timeout. */
  private TimeUnit timeUnit;
  
  /**
   * Default Constructor with 1 minute timeout.
   */
  public ThreadPoolJVMShutdownHook() {
    this(1, TimeUnit.MINUTES);
  }
  
  /**
   * Constructor with timeout and time unit.
   * @param timeout the timeout
   * @param timeUnit the time unit
   */
  public ThreadPoolJVMShutdownHook(long timeout, TimeUnit timeUnit) {
    this.timeout = timeout;
    this.timeUnit = timeUnit;
  }
  
  /**
   * {@inheritDoc}
   */
  public void run() {
    ThreadPool tp = ThreadPool.getDefaultThreadPool();
    tp.shutdown();
    // interrupt all running runnables
    try {
      if (!tp.executor.awaitTermination(timeout, timeUnit)) {
        logger.debug("Executor did not terminate in the specified time.");
        List<Runnable> droppedTasks = tp.executor.shutdownNow();
        logger.debug("Executor was abruptly shut down. " + droppedTasks.size() + " tasks will not be executed.");
      }
      if (!tp.scheduledExecutor.awaitTermination(timeout, timeUnit)) {
        logger.debug("Scheduled Executor did not terminate in the specified time.");
        List<Runnable> droppedTasks = tp.scheduledExecutor.shutdownNow();
        logger.debug("Scheduled Executor was abruptly shut down. " + droppedTasks.size() + " tasks will not be executed.");
      }
    } catch (InterruptedException e) {
      logger.warn("interrupted while shudown executors", e);
    }
  }
}
