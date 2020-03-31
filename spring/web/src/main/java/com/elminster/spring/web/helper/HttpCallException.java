package com.elminster.spring.web.helper;

/**
 * The HTTP call exception.
 *
 * @author jgu
 * @version 1.0
 */
public class HttpCallException extends RuntimeException {


  public HttpCallException() {
  }

  public HttpCallException(String message) {
    super(message);
  }

  public HttpCallException(String message, Throwable cause) {
    super(message, cause);
  }

  public <T> HttpCallException(Throwable cause) {
    super(cause);
  }
}
