package com.elminster.common.misc;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.elminster.common.util.StringUtil;

public class AsynchorizedFileWriter implements IWriter, Runnable {
  
  private static final Log logger = LogFactory.getLog(AsynchorizedFileWriter.class);
  private static final long DEFAULT_INTERVAL = 500;
  private final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(1024);
  private final long pollInterval;
  private final Writer fileWriter;
  private volatile boolean stop;
  
  public AsynchorizedFileWriter(String fileName) throws IOException {
    this(fileName, DEFAULT_INTERVAL);
  }
  
  public AsynchorizedFileWriter(String fileName, long pollInterval) throws IOException {
    this.pollInterval = pollInterval;
    fileWriter = new BufferedWriter(new FileWriter(fileName, true));
    stop = false;
  }

  @Override
  public IWriter append(String msg) throws IOException {
    try {
      queue.put(msg);
    } catch (InterruptedException e) {
      throw new IOException(e);
    }
    return this;
  }
  
  @Override
  public IWriter appendLn(String msg) throws IOException {
    try {
      queue.put(msg + StringUtil.newline());
    } catch (InterruptedException e) {
      throw new IOException(e);
    }
    return this;
  }

  @Override
  public void close() throws IOException {
    stop = true;
  }

  @Override
  public void run() {
    try {
      while (!stop) {
        try {
          String msg = queue.poll(pollInterval, TimeUnit.MICROSECONDS);
          try {
            if (null != msg) {
              fileWriter.append(msg);
            }
          } catch (IOException ioe) {
            logger.error(ioe);
          }
        } catch (InterruptedException ie) {
          logger.error(ie);
        }
      }
    } catch (Exception e) {
      
    } finally {
      if (null != fileWriter) {
        try {
          fileWriter.flush();
          fileWriter.close();
        } catch (IOException e) {
          logger.warn(e);
        }
      }
    }
  }
}
