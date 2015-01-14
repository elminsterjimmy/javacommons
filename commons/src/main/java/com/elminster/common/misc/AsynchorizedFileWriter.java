package com.elminster.common.misc;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.elminster.common.util.StringUtil;

public class AsynchorizedFileWriter implements IWriter, Runnable {
  
  private static final Log logger = LogFactory.getLog(AsynchorizedFileWriter.class);
  private final BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
  private final Writer fileWriter;
  private volatile boolean stop;
  
  public AsynchorizedFileWriter(String fileName) throws IOException {
    fileWriter = new BufferedWriter(new FileWriter(fileName));
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
          String msg = queue.poll(100, TimeUnit.MICROSECONDS);
          try {
            fileWriter.append(msg);
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
