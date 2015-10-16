package com.elminster.common.exception;

/**
 * The base exception contains an exception code.
 * 
 * @author jgu
 * @version 1.0
 */
public class BaseException extends Exception {

  /**
   * serial id.
   */
  private static final long serialVersionUID = 4004318643050048268L;
  
  public static final ErrorCode UNKNOWN_CODE = new ErrorCode("0xFFFFFFFFFF", "UNKNOWN");

  /** the exception code. */
  protected ErrorCode exceptionCode;
  
  protected BaseException() {
    this(UNKNOWN_CODE);
  }
  
  public BaseException(ErrorCode code) {
    this.exceptionCode = code;
  }
  
  protected BaseException(String message) {
    this(UNKNOWN_CODE, message);
  }
  
  public BaseException(ErrorCode code, String message) {
    super(message);
    this.exceptionCode = code;
  }
  
  protected BaseException(String message, Throwable cause) {
    this(UNKNOWN_CODE, message, cause);
  }
  
  public BaseException(ErrorCode code, String message, Throwable cause) {
    super(message, cause);
    this.exceptionCode = code;
  }
  
  protected BaseException(Throwable cause) {
    this(UNKNOWN_CODE, cause);
  }

  public BaseException(ErrorCode code, Throwable cause) {
    super(cause);
  }

  /**
   * Get the exception code.
   * @return the exception code
   */
  public ErrorCode getExceptionCode() {
    return exceptionCode;
  }
}
