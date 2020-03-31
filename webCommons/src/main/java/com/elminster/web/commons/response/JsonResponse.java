package com.elminster.web.commons.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The general JSON response.
 *
 * @author jgu
 * @version 1.0
 */
public class JsonResponse implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = -6882544319032795924L;
  /** the response status: OK. */
  public static final String STATUS_OK = "OK";
  /** the response status: ERROR. */
  public static final String STATUS_ERROR = "ERROR";
  
  /**
   * The json status.
   */
  private String status;
  /**
   * The json errors.
   */
  private List<ResponseError> errors;
  /**
   * The json data.
   */
  private Object data;

  public JsonResponse() {
    this.status = STATUS_OK;
  }
  
  /**
   * @return the status
   */
  public String getStatus() {
    return status;
  }
  /**
   * @param status the status to set
   */
  public JsonResponse setStatus(String status) {
    this.status = status;
    return this;
  }
  /**
   * @return the errors
   */
  public List<ResponseError> getErrors() {
    return errors;
  }
  /**
   * @param errors the errors to set
   */
  public JsonResponse setErrors(List<ResponseError> errors) {
    this.errors = errors;
    return this;
  }
  
  public JsonResponse addError(ResponseError error) {
    if (null == this.errors) {
      this.errors = new ArrayList<>();
    }
    this.errors.add(error);
    return this;
  }

  /**
   * @return the data
   */
  public Object getData() {
    return data;
  }

  /**
   * @param data the data to set
   */
  public JsonResponse setData(Object data) {
    this.data = data;
    return this;
  }
}