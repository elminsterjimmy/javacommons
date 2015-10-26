package com.elminster.common.config;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import com.elminster.common.observable.FileWatcher;
import com.elminster.common.pool.ThreadPool;
import com.elminster.common.pool.ThreadPoolListener;

/**
 * The dynamic configuration that using configuration files. All files will be watched by a 
 * thread to invoke the update if the file's last modify date is changed. 
 * 
 * @author jgu
 * @version 1.0
 */
public class DynamicConfiguration extends StandardConfiguration implements Observer {

  /** the file watchers. */
  protected FileWatcher[] fileWatchers;
  
  /**
   * Constructor.
   * @param configurationFiles the configuration files
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
      
    }
  }
  
  /**
   * Load the resource files.
   */
  protected void loadResources() {
    super.loadResources();
    if (null != configurationFiles) {
      ThreadPool pool = ThreadPool.getThreadPool();
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
