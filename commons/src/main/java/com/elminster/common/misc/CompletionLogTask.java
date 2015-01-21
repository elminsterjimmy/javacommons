package com.elminster.common.misc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.elminster.common.util.DateUtil;
import com.elminster.common.util.FileUtil;

/**
 * The task logs completion items. When the task starts, it will load the completed tasks is the log exists.
 * 
 * @author jgu
 * @version 1.0
 */
public class CompletionLogTask {
  
  /** the default interval. */
  private static final long DEFAULT_INTERVAL = DateUtil.MINUTE;
  /** the logger. */
  private static final Log logger = LogFactory.getLog(CompletionLogTask.class);
  /** the completion log path. */
  protected final String completionLogPath;
  /** the lock used to ensure the thread safe of read/write unloggedCompletionTasks List. */
  private Object LOCK = new Object();
  /** the completion tasks. */
  protected List<String> completionTasks;
  /** the unlogged completion tasks. */
  protected final List<String> unloggedCompletionTasks;
  /** the scheduled executor service. */
  protected final ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
  /** the log interval. */
  protected final long interval;
  /** the writer. */
  protected final IWriter writer;
  
  /**
   * Constructor.
   * @param path the completion log path
   */
  public CompletionLogTask(String path) {
    this(path, DEFAULT_INTERVAL, null);
  }
  
  public CompletionLogTask(String path, List<String> completionTasks) {
    this(path, DEFAULT_INTERVAL, completionTasks);
  }
  
  /**
   * Constructor.
   * @param path the completion log path
   * @param logInterval the log interval
   */
  public CompletionLogTask(String path, long logInterval, List<String> completionTasks) {
    this.completionLogPath = path;
    this.interval = logInterval;
    if (null == completionLogPath) {
      throw new IllegalArgumentException("completion log path cannot be null.");
    }
    if (null == completionTasks) {
      File file = new File(completionLogPath);
      if (file.exists()) {
        try {
          completionTasks = FileUtil.readFileByLine(completionLogPath);
        } catch (IOException e) {
          completionTasks = new ArrayList<String>();
        }
      } else {
        try {
          FileUtil.createNewFile(completionLogPath);
          completionTasks = new ArrayList<String>();
        } catch (IOException e) {
          throw new IllegalArgumentException("Failed to create completion log at " + completionLogPath);
        }
      }
    } else {
      this.completionTasks = completionTasks;
    }
    unloggedCompletionTasks = new ArrayList<String>();
    writer = WriterFactory.INSTANCE.getWriter(path);
    service.scheduleAtFixedRate(new CompletionLogWriterTask(), interval, interval, TimeUnit.MILLISECONDS);
  }
  
  /**
   * Complete task and write the completion task log.
   * @param taskLog the completion task log
   */
  public void completeTask(String taskLog) {
    this.completionTasks.add(taskLog);
    synchronized (LOCK) {
      this.unloggedCompletionTasks.add(taskLog);
    }
  }
  
  /**
   * Shutdown the log writer task.
   */
  public void shutdown() {
    try {
      writer.close();
    } catch (IOException e) {
    }
    service.shutdown();
  }
  
  /**
   * @return the completionLogPath
   */
  public String getCompletionLogPath() {
    return completionLogPath;
  }

  /**
   * @return the completionTasks
   */
  public List<String> getCompletionTasks() {
    return completionTasks;
  }

  /**
   * @return the unloggedCompletionTasks
   */
  public List<String> getUnloggedCompletionTasks() {
    return unloggedCompletionTasks;
  }

  /**
   * The log writer task.
   * 
   * @author jgu
   * @version 1.0
   */
  class CompletionLogWriterTask implements Runnable {
    @Override
    public void run() {
      try {
        synchronized (LOCK) {
          for (String msg : unloggedCompletionTasks) {
            writer.appendLn(msg);
          }
          unloggedCompletionTasks.clear();
        }
      } catch (IOException e) {
        logger.error("Write completion log file error. Cause: " + e.getMessage());
        throw new RuntimeException(e);
      }
    }
    
  }
}
