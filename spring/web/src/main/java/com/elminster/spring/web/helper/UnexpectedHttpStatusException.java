package com.elminster.spring.web.helper;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;

/**
 * The unexpected HTTP status Excetpion.
 *
 * @author jgu
 * @version 1.0
 */
public class UnexpectedHttpStatusException extends HttpCallException {

  /** the response. */
  private ResponseEntity<?> response;
  private Class<?> responseType;
  private ParameterizedTypeReference<?> parameterizedTypeReference;

  public UnexpectedHttpStatusException(String message, ResponseEntity<?> response, Class<?> responseType) {
    super(message);
    this.response = response;
    this.responseType = responseType;
  }

  public UnexpectedHttpStatusException(String message, ResponseEntity<?> response, ParameterizedTypeReference<?> parameterizedTypeReference) {
    super(message);
    this.response = response;
    this.responseType = responseType;
  }

  /**
   * Get the response.
   *
   * @return the response
   */
  public ResponseEntity<?> getResponse() {
    return this.response;
  }

  /**
   * Get the response type.
   *
   * @return the response type
   */
  public Class<?> getResponseType() {
    return responseType;
  }

  /**
   * Get the response type.
   *
   * @return the response type
   */
  public ParameterizedTypeReference<?> getParameterizedTypeReference() {
    return parameterizedTypeReference;
  }
}
