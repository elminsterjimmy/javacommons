package com.elminster.common.threadpool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.elminster.common.config.CommonConfiguration;
import com.elminster.common.util.ExceptionUtil;

/**
 * ThreadPool Configuration.
 * 
 * @author jinggu
 * @version 1.0
 */
public class ThreadPoolConfiguration extends CommonConfiguration {

  private static final Log logger = LogFactory.getLog(ThreadPoolConfiguration.class);

  private static final String CONFIG_NAME = "threadpool.properties";

  /** the thread pool core size. */
  public static final IntegerKey CORE_POOL_SIZE = new IntegerKey("threadpool.core.size", 10);
  /** the thread pool max size. */
  public static final IntegerKey MAX_POOL_SIZE = new IntegerKey("threadpool.max.size", 10);
  /** the thread pool keep alive time (unit ms). */
  public static final LongKey KEEP_ALIVE_TIME = new LongKey("threadpool.keep.alive.time", 1000L);
  /** the thread pool name. */
  public static final StringKey POOL_NAME = new StringKey("threadpool.name", "threadpool");
  /** the thread pool uses daemon thread? */
  public static final BooleanKey DAEMON_THREAD = new BooleanKey("threadpool.daemo", false);
  /** the thread pool uses rejected policy. */
  public static final StringKey REJECTED_POLICY = new StringKey("threadpool.rejected.policy", DefaultRejectedPolicy.class.getName());

  public static final ThreadPoolConfiguration INSTANCE = new ThreadPoolConfiguration();

  @Override
  protected void loadResources() {
    super.loadResources();
    File configFile = new File(CONFIG_NAME);
    if (!configFile.exists()) {
      logger.warn(String.format("[%s] is not found, use default configuration.", CONFIG_NAME));
    } else {
      try (InputStream fis = new FileInputStream(configFile)) {
        properties.load(fis);
      } catch (IOException e) {
        logger.error(String.format("Failed to load [%s]. Cause [%s].", CONFIG_NAME, ExceptionUtil.getFullStackTrace(e)));
        logger.warn(String.format("[%s] is not found, use default configuration.", CONFIG_NAME));
      }
    }
  }
}
