package com.elminster.common.pool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
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
public class ThreadPool {
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
  private ThreadPoolExecutor executor;

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
   * @param cfg the config
   */
  private void quickInit(IConfigProvider cfg) {
    executor = new ThreadPoolExecutor(
        cfg.getIntegerProperty(CORE_POOL_SIZE, 10),
        cfg.getIntegerProperty(MAX_POOL_SIZE, 10),
        cfg.getLongProperty(KEEP_ALIVE_TIME, 0L),
        TimeUnit.MILLISECONDS,
        new ArrayBlockingQueue<Runnable>(cfg.getIntegerProperty(CORE_POOL_SIZE, 10)));
  }
  
  /**
   * Get the thread pool.
   * @return the singleton thread pool
   */
  public static ThreadPool getThreadPool() {
    return pool;
  }

  /**
   * Submit a callable.
   * @param callable the callable
   * @return a futrue
   */
  public Future<?> submit(Callable<?> callable) {
    return executor.submit(callable);
  }

  /**
   * Execute a runnable.
   * @param runnable the runnable
   */
  public void execute(Runnable runnable) {
    executor.execute(runnable);
  }

  /**
   * Shutdown the pool.
   */
  public void shutdown() {
    executor.shutdown();
  }
}