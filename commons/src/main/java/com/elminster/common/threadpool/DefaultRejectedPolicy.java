package com.elminster.common.threadpool;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default rejected policy as abort and log the status.
 * 
 * @author jinggu
 * @version 1.0
 */
public class DefaultRejectedPolicy implements RejectedExecutionHandler {

  /** the logger. */
  public static final Logger logger = LoggerFactory.getLogger(DefaultRejectedPolicy.class);
  
  @Override
  public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
    String msg = String.format("Thread pool is full!" +
        " Pool Size: %d (active: %d, core: %d, max: %d, largest: %d), Task: %d (completed: %d)," +
        " Executor status: (isShutdown:%s, isTerminated:%s, isTerminating:%s)." ,
        e.getPoolSize(), e.getActiveCount(), e.getCorePoolSize(), e.getMaximumPoolSize(), e.getLargestPoolSize(),
        e.getTaskCount(), e.getCompletedTaskCount(), e.isShutdown(), e.isTerminated(), e.isTerminating());
    logger.warn(msg);
    throw new RejectedExecutionException(msg);
  }
}
