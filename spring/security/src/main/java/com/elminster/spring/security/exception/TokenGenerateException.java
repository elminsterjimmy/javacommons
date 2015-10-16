package com.elminster.spring.security.exception;

import com.elminster.common.exception.BaseException;
import com.elminster.common.exception.ErrorCode;

public class TokenGenerateException extends BaseException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public TokenGenerateException() {
    super();
  }

  public TokenGenerateException(ErrorCode code, String message, Throwable cause) {
    super(code, message, cause);
  }

  public TokenGenerateException(ErrorCode code, String message) {
    super(code, message);
  }

  public TokenGenerateException(ErrorCode code, Throwable cause) {
    super(code, cause);
  }

  public TokenGenerateException(ErrorCode code) {
    super(code);
  }

  public TokenGenerateException(String message, Throwable cause) {
    super(message, cause);
  }

  public TokenGenerateException(String message) {
    super(message);
  }

  public TokenGenerateException(Throwable cause) {
    super(cause);
  }
}
