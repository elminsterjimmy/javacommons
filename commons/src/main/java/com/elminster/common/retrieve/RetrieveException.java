package com.elminster.common.retrieve;

import com.elminster.common.exception.BaseException;
import com.elminster.common.exception.ErrorCode;

public class RetrieveException extends BaseException {

  /**
   * 
   */
  private static final long serialVersionUID = -5565295860222806264L;

  public RetrieveException() {
    super();
  }

  public RetrieveException(ErrorCode code, String message, Throwable cause) {
    super(code, message, cause);
  }

  public RetrieveException(ErrorCode code, String message) {
    super(code, message);
  }

  public RetrieveException(ErrorCode code, Throwable cause) {
    super(code, cause);
  }

  public RetrieveException(ErrorCode code) {
    super(code);
  }

  public RetrieveException(String message, Throwable cause) {
    super(message, cause);
  }

  public RetrieveException(String message) {
    super(message);
  }

  public RetrieveException(Throwable cause) {
    super(cause);
  }
}
