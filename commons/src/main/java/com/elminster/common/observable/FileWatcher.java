package com.elminster.common.observable;

import java.io.File;
import java.util.Observable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.elminster.common.util.DateUtil;

/**
 * Watch the specified file, notify observer when the file last modify time is changed.
 * Use like this:
 * <code>
 * <pre>
 * ThreadPool pool = Thread.getThreadPool();
 * pool.quickInit();
 * FileWatcher fw = new FileWatch(File);
 * fw.addObserver(this);
 * pool.execute(fw);
 * </pre>
 * </code>
 * @author Gu
 * @version 1.0
 */
public class FileWatcher extends Observable implements Runnable {
  
  /** Watch interval. Set to 10 seconds as default. */
  private static final long DEFAULT_WATCH_INTERVAL = 10 * DateUtil.SECOND;
  /** the logger. */
  private static final Log logger = LogFactory.getLog(FileWatcher.class);
  
  /** the file to watch. */
  protected File file;
  /** last modify time of the watched file. */
  protected long lastModifyTime;
  /** watch interval. */
  protected long watchInterval = DEFAULT_WATCH_INTERVAL;
  /** watch stop flag. */
  protected volatile boolean stop;
  
  /**
   * Constructor.
   * 
   * @param file the file to watch
   */
  public FileWatcher(File file) {
    this.file = file;
    this.lastModifyTime = file.lastModified();
  }
  
  /**
   * Stop watching.
   */
  public void stopWatch() {
    if (!stop) {
      stop = true;
    }
  }
  
  /**
   * Get the watched file.
   * @return the watched file
   */
  public File getFile() {
    return this.file;
  }
  
  /**
   * Set the watch interval.
   * @param watchInterval watch interval
   */
  public void setWatchInterval(long watchInterval) {
    this.watchInterval = watchInterval;
  }
  
  /**
   * Watch specified file and notify observers when file last modify time is changed.
   */
  @Override
  public void run() {
    while (!stop) {
      if (!file.exists()) {
        stopWatch();
        throw new RuntimeException("Watched file: " + file.getAbsolutePath() + " is not exist.");
      }
      
      if (Thread.currentThread().isInterrupted()) {
        if (logger.isDebugEnabled()) {
          logger.debug("File watcher interrupted! ");
        }
        stop = true;
      }
      
      long lastModified = file.lastModified();
      if (lastModified != lastModifyTime) {
        lastModifyTime = lastModified;
        setChanged();
        notifyObservers(this);
      }
      try {
        Thread.sleep(watchInterval);
      } catch (InterruptedException e) {
        if (logger.isDebugEnabled()) {
          logger.debug("File watcher interrupted! ", e);
        }
        stop = true;
      }
    }
  }
}
