package com.elminster.common.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elminster.common.util.ExceptionUtil;

public class LogUncatchedExceptionHandler implements UncatchedExceptionHandler {
  
  private static final Logger logger = LoggerFactory.getLogger(LogUncatchedExceptionHandler.class);
  
  @Override
  public void handleUncatchedException(Throwable t) {
    logger.error(ExceptionUtil.getStackTrace(t));
  }
}
