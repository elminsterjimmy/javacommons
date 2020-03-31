package com.elminster.common.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * The abstract Exec Stream handler.
 *
 * @author jgu
 * @version 1.0
 */
abstract public class AbstractExecStreamHandler implements ExecuteStreamHandler {

  private static final Logger logger = LoggerFactory.getLogger(AbstractExecStreamHandler.class);

  protected InputStream stdoutIs;
  protected OutputStream stdinOs;
  protected InputStream stderrIs;

  @Override
  public void setProcessInputStream(InputStream inputStream) throws IOException {
    this.stdoutIs = inputStream;
  }

  @Override
  public void setProcessOutputStream(OutputStream outputStream) throws IOException {
    this.stdinOs = outputStream;
  }

  @Override
  public void setProcessErrorStream(InputStream inputStream) throws IOException {
    this.stderrIs = inputStream;
  }

  @Override
  public void start() throws IOException {
    if (logger.isDebugEnabled()) {
      logger.debug("start exec...");
    }
  }

  @Override
  public void stop() throws IOException {
    if (logger.isDebugEnabled()) {
      logger.debug("stop exec...");
    }
    if (null != stderrIs) {
      stderrIs.close();
    }
    if (null != stdinOs) {
      stdinOs.close();
    }
    if (null != stdoutIs) {
      stdoutIs.close();
    }
  }
}
