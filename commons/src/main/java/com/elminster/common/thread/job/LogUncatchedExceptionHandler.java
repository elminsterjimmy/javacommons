package com.elminster.common.thread.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elminster.common.util.ExceptionUtil;

/**
 * The UncatchedExceptionHandler to log the error in the job.
 *
 * @author jgu
 * @version 1.0
 */
public class LogUncatchedExceptionHandler implements UncatchedExceptionHandler {

  /** the logger. */
  private static final Logger logger = LoggerFactory.getLogger(LogUncatchedExceptionHandler.class);

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleUncatchedException(Throwable t) {
    logger.error(ExceptionUtil.getStackTrace(t));
  }
}
