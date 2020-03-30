package com.elminster.common.thread.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The named thread factory.
 * 
 * @author jinggu
 * @version 1.0
 */
public class NamedThreadFactory implements ThreadFactory {
  
  private final AtomicInteger threadNum = new AtomicInteger(1);

  private final String prefix;

  private final boolean daemon;

  private final ThreadGroup threadGroup;

  public NamedThreadFactory(String prefix) {
    this(prefix, false);
  }

  public NamedThreadFactory(String prefix, boolean daemo) {
    this.prefix = prefix;
    this.daemon = daemo;
    SecurityManager s = System.getSecurityManager();
    threadGroup = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
  }

  public Thread newThread(Runnable runnable) {
    String name = prefix + threadNum.getAndIncrement();
    Thread ret = new Thread(threadGroup, runnable, name, 0);
    ret.setDaemon(daemon);
    return ret;
  }
  
  public String getPrefix() {
    return prefix;
  }

  public ThreadGroup getThreadGroup() {
    return threadGroup;
  }
}
