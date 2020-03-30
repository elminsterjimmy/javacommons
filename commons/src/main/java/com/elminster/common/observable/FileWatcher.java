package com.elminster.common.observable;

import com.elminster.common.id.AtomicLongIdGenerator;
import com.elminster.common.id.IdGenerator;
import com.elminster.common.id.IdGeneratorFactory;
import com.elminster.common.thread.job.IJob;
import com.elminster.common.thread.job.IJobMonitor;
import com.elminster.common.thread.job.Job;
import com.elminster.common.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Observable;

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
  private static final Logger logger = LoggerFactory.getLogger(FileWatcher.class);

  /** the file to watch. */
  protected File file;
  /** last modify time of the watched file. */
  protected long lastModifyTime;
  /** watch interval. */
  final protected long watchInterval;
  /** the id generator */
  private IdGenerator idGenerator = IdGeneratorFactory.getIdGeneratorFacotry().getInstance(AtomicLongIdGenerator.class);;
  /** the watch job. */
  private IJob watchJob;

  /**
   * Constructor the FileWatcher to watch the file.
   * @param file the file to watch
   */
  public FileWatcher(File file) {
    this(file, DEFAULT_WATCH_INTERVAL);
  }

  /**
   * Constructor the FileWatcher to watch the file.
   * @param file the file to watch
   * @param watchInterval the watch interval
   */
  public FileWatcher(File file, long watchInterval) {
    if (null == file) {
      throw new IllegalArgumentException("file cannot be NULL.");
    }
    if (!file.exists()) {
      throw new IllegalArgumentException(String.format("file [%s] does NOT exist.", file));
    }
    this.file = file;
    this.lastModifyTime = file.lastModified();
    this.watchInterval = watchInterval;
    String jobName = String.format("FileWatcher [%s]", file.getName());
    watchJob = new Job((long)idGenerator.nextId(), jobName) {
      @Override
      protected JobStatus doWork(IJobMonitor monitor) throws Throwable {
        watch(monitor);
        return JobStatus.DONE;
      }
    };
  }

  /**
   * {@inheritDoc}
   */
  public void run() {
    watchJob.run();
  }
  
  /**
   * Stop watching.
   */
  public void stopWatch() {
    watchJob.cancel();
  }
  
  /**
   * Get the watched file.
   * @return the watched file
   */
  public File getFile() {
    return this.file;
  }

  /**
   * Get the watch interval.
   * @return the watch interval
   */
  public long getWatchInterval() {
    return watchInterval;
  }
  
  /**
   * Watch specified file and notify observers when file last modify time is changed.
   */
  public void watch(IJobMonitor monitor) throws Throwable {
    while (!monitor.isCancelled()) {
      if (!file.exists()) {
        stopWatch();
        throw new RuntimeException("Watched file: " + file.getAbsolutePath() + " is not exist.");
      }
      
      if (Thread.currentThread().isInterrupted()) {
        if (logger.isDebugEnabled()) {
          logger.debug("File watcher interrupted! ");
        }
        throw new InterruptedException();
      }
      
      long lastModified = file.lastModified();
      if (lastModified != lastModifyTime) {
        lastModifyTime = lastModified;
        setChanged();
        notifyObservers(this);
      }
      Thread.sleep(watchInterval);
    }
  }
}
