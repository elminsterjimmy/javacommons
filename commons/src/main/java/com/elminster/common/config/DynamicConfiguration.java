package com.elminster.common.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

import com.elminster.common.observable.FileWatcher;
import com.elminster.common.threadpool.ThreadPool;
import com.elminster.common.threadpool.ThreadPoolListener;

/**
 * The dynamic configuration that using configuration files. All files will be watched by a thread to invoke the update
 * if the file's last modify date is changed.
 * 
 * @author jgu
 * @version 1.0
 */
public class DynamicConfiguration extends StandardConfiguration implements Observer {

  /** the file watchers. */
  protected FileWatcher[] fileWatchers;

  protected Map<File, Properties> propMap = new HashMap<>();
  
  /**
   * Constructor.
   * 
   * @param configurationFiles
   *          the configuration files
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
      InputStream is = null;
      try {
        is = new FileInputStream(file);
        Properties prop = propMap.get(file);
        if (null == prop) {
          prop = new Properties();
        }
        prop.clear();
        prop.load(is);
        propMap.put(file, prop);
      } catch (IOException e) {
        throw new RuntimeException(String.format("exception while reloading configuration [%s] at [%s].",
            file.getName(), file.getAbsoluteFile()), e);
      } finally {
        if (null != is) {
          try {
            is.close();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      }
    }
  }

  /**
   * Load the resource files.
   */
  protected void loadResources() {
    super.loadResources();
    if (null != configurationFiles) {
      ThreadPool pool = ThreadPool.getDefaultThreadPool();
      pool.addThreadPoolListener(new ThreadPoolListener() {

        @Override
        public void onShutdown() {
          if (null != fileWatchers) {
            for (FileWatcher fw : fileWatchers) {
              fw.stopWatch();
            }
          }
        }

      });
      fileWatchers = new FileWatcher[configurationFiles.length];
      int i = 0;
      for (String configurationFile : configurationFiles) {
        fileWatchers[i] = new FileWatcher(new File(configurationFile));
        fileWatchers[i].addObserver(this);
        pool.execute(fileWatchers[i]);
      }
    }
  }

}
