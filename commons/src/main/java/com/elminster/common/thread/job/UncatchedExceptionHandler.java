package com.elminster.common.thread.job;

/**
 * The UncatchedExceptionHandler.
 *
 * @author jgu
 * @version 1.0
 */
public interface UncatchedExceptionHandler {

  /**
   * Handle the uncatched exception.
   * @param t the throwable
   */
  void handleUncatchedException(Throwable t);
}
