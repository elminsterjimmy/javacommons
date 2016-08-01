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
  public static final String CORE_POOL_SIZE = "threadpool.core.size";
  /** the thread pool max size. */
  public static final String MAX_POOL_SIZE = "threadpool.max.size";
  /** the thread pool keep alive time (unit ms). */
  public static final String KEEP_ALIVE_TIME = "threadpool.keep.alive.time";
  
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
