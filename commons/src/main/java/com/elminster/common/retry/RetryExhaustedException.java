package com.elminster.common.retry;

/**
 * The exception when the retry gets exhausted.
 *
 * @author jgu
 * @version 1.0
 */
public class RetryExhaustedException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public RetryExhaustedException() {
    super();
  }
}
