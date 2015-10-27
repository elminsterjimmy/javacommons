package com.elminster.web.commons.response;

import com.elminster.common.exception.BaseException;

/**
 * The JSON response builder.
 * 
 * @author jgu
 * @version 1.0
 */
public class JsonResponseBuilder {
  /** The builder instance. */
  public final static JsonResponseBuilder INSTANCE = new JsonResponseBuilder();
  
  /**
   * Constructor.
   */
  private JsonResponseBuilder() {}
  
  /**
   * Build error JSON response with exception.
   * @param e the exception
   * @return error JSON response
   */
  public JsonResponse buildErrorJsonResponse(Exception e) {
    JsonResponse jsonResponse = new JsonResponse();
    jsonResponse.setStatus(JsonResponse.STATUS_ERROR);
    jsonResponse.addError(generateResponseError(e));
    return jsonResponse;
  }
  
  /**
   * Build error JSON response with error message.
   * @param errorMessage error message
   * @return error JSON response
   */
  public JsonResponse buildErrorJsonResponse(String errorMessage) {
    JsonResponse jsonResponse = new JsonResponse();
    jsonResponse.setStatus(JsonResponse.STATUS_ERROR);
    jsonResponse.addError(generateResponseError(errorMessage));
    return jsonResponse;
  }
  
  /**
   * Build a new JSON response.
   * @return a new JSON response
   */
  public JsonResponse buildJsonResponse() {
    return new JsonResponse();
  }
  
  /**
   * Build a new JSON response with specified data.
   * @param data the data
   * @return a new JSON response with specified data
   */
  public JsonResponse buildJsonResponse(Object data) {
    JsonResponse jsonResponse = new JsonResponse();
    jsonResponse.setData(data);
    return jsonResponse;
  }
  
  /**
   * Generate response error by specified exception.
   * @param e the exception
   * @return response error
   */
  public ResponseError generateResponseError(Exception e) {
    ResponseError error = new ResponseError();
    if (e instanceof BaseException) {
      error.setErrorCode(((BaseException)e).getExceptionCode().getCode());
    } else {
      error.setErrorCode(BaseException.UNKNOWN_CODE.getCode());
    }
    error.setErrorMessage(e.getMessage());
    error.setLocalizedErrorMessage(e.getLocalizedMessage());
    return error;
  }
  
  /**
   * Generate response error by specified error message.
   * @param errorMessage the error message
   * @return response error
   */
  public ResponseError generateResponseError(String errorMessage) {
    return generateResponseError(BaseException.UNKNOWN_CODE.getCode(), errorMessage);
  }
  
  /**
   * Generate response error by specified error message.
   * @param code the error code
   * @param errorMessage the error message
   * @return response error
   */
  public ResponseError generateResponseError(String code, String errorMessage) {
    ResponseError error = new ResponseError();
    error.setErrorCode(code);
    error.setErrorMessage(errorMessage);
    error.setLocalizedErrorMessage(errorMessage);
    return error;
  }
}
