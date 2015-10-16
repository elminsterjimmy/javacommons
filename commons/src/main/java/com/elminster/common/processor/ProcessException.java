package com.elminster.common.processor;

import com.elminster.common.exception.BaseException;
import com.elminster.common.exception.ErrorCode;

public class ProcessException extends BaseException {

  /**
   * 
   */
  private static final long serialVersionUID = 5764497921671656906L;

  public ProcessException() {
    super();
  }

  public ProcessException(ErrorCode code, String message, Throwable cause) {
    super(code, message, cause);
  }

  public ProcessException(ErrorCode code, String message) {
    super(code, message);
  }

  public ProcessException(ErrorCode code, Throwable cause) {
    super(code, cause);
  }

  public ProcessException(ErrorCode code) {
    super(code);
  }

  public ProcessException(String message, Throwable cause) {
    super(message, cause);
  }

  public ProcessException(String message) {
    super(message);
  }

  public ProcessException(Throwable cause) {
    super(cause);
  }
}
