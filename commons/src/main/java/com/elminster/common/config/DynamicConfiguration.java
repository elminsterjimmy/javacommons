package com.elminster.common.config;

import com.elminster.common.config.key.Key;
import com.elminster.common.constants.Constants;
import com.elminster.common.misc.LockWrapper;
import com.elminster.common.observable.FileWatcher;
import com.elminster.common.threadpool.ThreadPool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The dynamic configuration that using configuration files. All files will be watched by a thread to invoke the update
 * if the file's last modify date is changed.
 *
 * @author jgu
 * @version 1.0
 */
public class DynamicConfiguration extends StandardConfiguration implements Observer {

  /** the file watch interval: {@literal 30} sec. */
  private static final long WATCH_INTERVAL = 30 * Constants.TimeUnit.SECOND_IN_MS;
  /**
   * the file watchers.
   */
  protected FileWatcher[] fileWatchers;

  protected Map<File, Properties> propMap = new HashMap<>();

  /**
   * the read write lock.
   */
  private ReadWriteLock rwLock = new ReentrantReadWriteLock();
  private Lock readLock = rwLock.readLock();
  private Lock writeLock = rwLock.writeLock();

  /**
   * Constructor.
   *
   * @param configurationFiles
   *     the configuration files
   */
  public DynamicConfiguration(String... configurationFiles) {
    super(configurationFiles);
  }

  /**
   * Update the properties if the file is updated.
   */
  @Override
  public void update(Observable o, Object arg) {
    if (arg instanceof FileWatcher) {
      FileWatcher fw = (FileWatcher) arg;
      File file = fw.getFile();
      if (logger.isDebugEnabled()) {
        logger.debug(String.format("Update Configuration file [%s]", file.getName()));
      }
      // reload the file
      _loadResource(file);
    }
  }

  private Properties mergeProperties() {
    Properties merged = new Properties();
    for (Properties p : propMap.values()) {
      merged.putAll(p);
    }
    return merged;
  }

  /**
   * Load the resource files.
   */
  protected void loadResources() {
    if (null != configurationFiles) {
      ThreadPool pool = ThreadPool.getDefaultThreadPool();
      pool.addThreadPoolListener(() -> {
        if (null != fileWatchers) {
          for (FileWatcher fw : fileWatchers) {
            fw.stopWatch();
          }
        }
      });
      fileWatchers = new FileWatcher[configurationFiles.length];
      int i = 0;
      for (String configurationFile : configurationFiles) {
        File file = new File(configurationFile);
        _loadResource(file);
        fileWatchers[i] = new FileWatcher(file, WATCH_INTERVAL);
        fileWatchers[i].addObserver(this);
        pool.execute(fileWatchers[i]);
      }
    }
  }

  private void _loadResource(File file) {
    // reload the file
    try (InputStream is = new FileInputStream(file)) {
      Properties prop = propMap.get(file);
      if (null == prop) {
        prop = new Properties();
      }
      synchronized (prop) {
        prop.clear();
        prop.load(is);
        propMap.put(file, prop);
        Properties mergedProperties = mergeProperties();
        try {
          writeLock.lock();
          properties = mergedProperties;
        } finally {
          writeLock.unlock();
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(String.format("exception while reloading configuration [%s] at [%s].",
          file.getName(), file.getAbsoluteFile()), e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getStringProperty(String key) {
    return LockWrapper.wrapperWithLock(readLock, () -> super.getStringProperty(key));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getStringProperty(String key, String defaultValue) {
    return LockWrapper.wrapperWithLock(readLock, () -> super.getStringProperty(key, defaultValue));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getStringProperty(StringKey key) {
    return LockWrapper.wrapperWithLock(readLock, () -> super.getStringProperty(key));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Integer getIntegerProperty(String key) {
    return LockWrapper.wrapperWithLock(readLock, () -> super.getIntegerProperty(key));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Integer getIntegerProperty(String key, Integer defaultValue) {
    return LockWrapper.wrapperWithLock(readLock, () -> super.getIntegerProperty(key, defaultValue));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Integer getIntegerProperty(IntegerKey key) {
    return LockWrapper.wrapperWithLock(readLock, () -> super.getIntegerProperty(key));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Long getLongProperty(String key) {
    return LockWrapper.wrapperWithLock(readLock, () -> super.getLongProperty(key));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Long getLongProperty(String key, Long defaultValue) {
    return LockWrapper.wrapperWithLock(readLock, () -> super.getLongProperty(key, defaultValue));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Long getLongProperty(LongKey key) {
    return LockWrapper.wrapperWithLock(readLock, () -> super.getLongProperty(key));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Float getFloatProperty(String key) {
    return LockWrapper.wrapperWithLock(readLock, () -> super.getFloatProperty(key));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Float getFloatProperty(String key, Float defaultValue) {
    return LockWrapper.wrapperWithLock(readLock, () -> super.getFloatProperty(key, defaultValue));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Float getFloatProperty(FloatKey key) {
    return LockWrapper.wrapperWithLock(readLock, () -> super.getFloatProperty(key));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Double getDoubleProperty(String key) {
    return LockWrapper.wrapperWithLock(readLock, () -> super.getDoubleProperty(key));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Double getDoubleProperty(String key, Double defaultValue) {
    return LockWrapper.wrapperWithLock(readLock, () -> super.getDoubleProperty(key, defaultValue));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Double getDoubleProperty(DoubleKey key) {
    return LockWrapper.wrapperWithLock(readLock, () -> super.getDoubleProperty(key));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Boolean getBooleanProperty(String key) {
    return LockWrapper.wrapperWithLock(readLock, () -> super.getBooleanProperty(key));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Boolean getBooleanProperty(String key, Boolean defaultValue) {
    return LockWrapper.wrapperWithLock(readLock, () -> super.getBooleanProperty(key, defaultValue));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Boolean getBooleanProperty(BooleanKey key) {
    return LockWrapper.wrapperWithLock(readLock, () -> super.getBooleanProperty(key));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setProperty(String key, String value) {
    LockWrapper.wrapperWithLock(writeLock, () -> super.setProperty(key, value));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setProperty(String key, Boolean boolValue) {
    LockWrapper.wrapperWithLock(writeLock, () -> super.setProperty(key, boolValue));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setProperty(String key, Integer intValue) {
    LockWrapper.wrapperWithLock(writeLock, () -> super.setProperty(key, intValue));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setProperty(String key, Long longValue) {
    LockWrapper.wrapperWithLock(writeLock, () -> super.setProperty(key, longValue));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setProperty(String key, Float floatValue) {
    LockWrapper.wrapperWithLock(writeLock, () -> super.setProperty(key, floatValue));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setProperty(String key, Double doubleValue) {
    LockWrapper.wrapperWithLock(writeLock, () -> super.setProperty(key, doubleValue));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <E> void setProperties(Key<E> key) {
    LockWrapper.wrapperWithLock(writeLock, () -> super.setProperties(key));
  }
}
