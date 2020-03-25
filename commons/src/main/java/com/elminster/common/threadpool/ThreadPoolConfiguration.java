package com.elminster.common.threadpool;

import com.elminster.common.config.CommonConfiguration;
import com.elminster.common.config.key.KeyBuilder;

import static com.elminster.common.config.key.Key.*;

/**
 * ThreadPool Configuration.
 * 
 * @author jinggu
 * @version 1.0
 */
public class ThreadPoolConfiguration extends CommonConfiguration {

  /** the thread pool core size. */
  public static final IntegerKey CORE_POOL_SIZE = KeyBuilder.integerKey("threadpool.core.size", 100);
  /** the thread pool max size. */
  public static final IntegerKey MAX_POOL_SIZE = KeyBuilder.integerKey("threadpool.max.size", 200);
  /** the thread pool core size. */
  public static final IntegerKey BLOCKING_QUEUE_SIZE = KeyBuilder.integerKey("threadpool.blocking.queue.size", 100);
  /** the thread pool keep alive time (unit ms). */
  public static final LongKey KEEP_ALIVE_TIME = KeyBuilder.longKey("threadpool.keep.alive.time", 1000L);
  /** the thread pool name. */
  public static final StringKey POOL_NAME = KeyBuilder.stringKey("threadpool.name", "threadpool");
  /** the thread pool uses daemon thread? */
  public static final BooleanKey DAEMON_THREAD = KeyBuilder.booleanKey("threadpool.daemo", false);
  /** the thread pool uses rejected policy. */
  public static final StringKey REJECTED_POLICY = KeyBuilder.stringKey("threadpool.rejected.policy", DefaultRejectedPolicy.class.getName());

  public static final ThreadPoolConfiguration INSTANCE = new ThreadPoolConfiguration();
  
  public ThreadPoolConfiguration() {
    super();
    setProperties(CORE_POOL_SIZE);
    setProperties(MAX_POOL_SIZE);
    setProperties(BLOCKING_QUEUE_SIZE);
    setProperties(KEEP_ALIVE_TIME);
    setProperties(POOL_NAME);
    setProperties(DAEMON_THREAD);
    setProperties(REJECTED_POLICY);
  }
}
