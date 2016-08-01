package com.elminster.common.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.elminster.common.util.ExceptionUtil;

public class NoneUncatchedExceptionHandler implements UncatchedExceptionHandler {
  
  private static final Log logger = LogFactory.getLog(NoneUncatchedExceptionHandler.class);
  
  @Override
  public void handleUncatchedException(Throwable t) {
    logger.error(ExceptionUtil.getCause(t));
  }
}
