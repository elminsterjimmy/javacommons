package com.elminster.web.commons.response;

import com.elminster.common.exception.BaseException;

public class JsonResponseBuilder {

  public final static JsonResponseBuilder INSTANCE = new JsonResponseBuilder();
  
  private JsonResponseBuilder() {}
  
  public JsonResponse buildErrorJsonResponse(Exception e) {
    JsonResponse jsonResponse = new JsonResponse();
    jsonResponse.setStatus(JsonResponse.STATUS_ERROR);
    jsonResponse.addError(generateResponseError(e));
    return jsonResponse;
  }
  
  public JsonResponse buildErrorJsonResponse(String errorMessage) {
    JsonResponse jsonResponse = new JsonResponse();
    jsonResponse.setStatus(JsonResponse.STATUS_ERROR);
    jsonResponse.addError(generateResponseError(errorMessage));
    return jsonResponse;
  }
  
  public JsonResponse buildJsonResponse() {
    return new JsonResponse();
  }
  
  public JsonResponse buildJsonResponse(Object data) {
    JsonResponse jsonResponse = new JsonResponse();
    jsonResponse.setData(data);
    return jsonResponse;
  }
  
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
  
  public ResponseError generateResponseError(String errorMessage) {
    ResponseError error = new ResponseError();
    error.setErrorCode(BaseException.UNKNOWN_CODE.getCode());
    error.setErrorMessage(errorMessage);
    error.setLocalizedErrorMessage(errorMessage);
    return error;
  }
}
