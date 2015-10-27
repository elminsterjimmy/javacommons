package com.elminster.web.commons.response;

/**
 * The response's detail error.
 * 
 * @author jgu
 * @version 1.0
 */
public class ResponseError {
  
  /** the error code. */
  private String errorCode;
  /** the error message. */
  private String errorMessage;
  /** the localized error message. */
  private String localizedErrorMessage;
  /**
   * @return the errorCode
   */
  public String getErrorCode() {
    return errorCode;
  }
  /**
   * @param errorCode the errorCode to set
   */
  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }
  /**
   * @return the errorMessage
   */
  public String getErrorMessage() {
    return errorMessage;
  }
  /**
   * @param errorMessage the errorMessage to set
   */
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
  /**
   * @return the localizedErrorMessage
   */
  public String getLocalizedErrorMessage() {
    return localizedErrorMessage;
  }
  /**
   * @param localizedErrorMessage the localizedErrorMessage to set
   */
  public void setLocalizedErrorMessage(String localizedErrorMessage) {
    this.localizedErrorMessage = localizedErrorMessage;
  }
}
