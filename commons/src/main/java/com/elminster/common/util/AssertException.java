package com.elminster.common.util;

/**
 * The assert exception.
 * 
 * @author jgu
 * @version 1.0
 */
public class AssertException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -3628044668568227371L;

  /**
   * 
   */
  public AssertException() {
    super();
  }

  /**
   * @param message
   * @param cause
   * @param enableSuppression
   * @param writableStackTrace
   */
  public AssertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  /**
   * @param message
   * @param cause
   */
  public AssertException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * @param message
   */
  public AssertException(String message) {
    super(message);
  }

  /**
   * @param cause
   */
  public AssertException(Throwable cause) {
    super(cause);
  }
}
