package com.elminster.common.pool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.elminster.common.config.CommonConfiguration;
import com.elminster.common.util.ExceptionUtil;

public class ThreadPoolConfiguration extends CommonConfiguration {

  private static final Log logger = LogFactory.getLog(ThreadPoolConfiguration.class);
  
  private static final String CONFIG_NAME = "threadpool.properties";
  
  /** the thread pool core size. */
  public static final IntegerKey CORE_POOL_SIZE = new IntegerKey("threadpool.core.size", 10);
  /** the thread pool max size. */
  public static final IntegerKey MAX_POOL_SIZE = new IntegerKey("threadpool.max.size", 10);
  /** the thread pool keep alive time (unit ms). */
  public static final LongKey KEEP_ALIVE_TIME = new LongKey("threadpool.keep.alive.time", 1000L);
  
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
      }
    }
  }
}
