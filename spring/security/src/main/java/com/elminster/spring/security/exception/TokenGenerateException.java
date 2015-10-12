package com.elminster.spring.security.exception;

import com.elminster.common.exception.BaseException;

public class TokenGenerateException extends BaseException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public TokenGenerateException() {
    super();
    // TODO Auto-generated constructor stub
  }

  public TokenGenerateException(int code, String message, Throwable cause) {
    super(code, message, cause);
  }

  public TokenGenerateException(int code, String message) {
    super(code, message);
  }

  public TokenGenerateException(int code, Throwable cause) {
    super(code, cause);
  }

  public TokenGenerateException(int code) {
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
