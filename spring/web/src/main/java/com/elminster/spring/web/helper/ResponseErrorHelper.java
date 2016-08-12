package com.elminster.spring.web.helper;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.elminster.web.commons.response.ResponseError;

public class ResponseErrorHelper {
  
  public static final ResponseErrorHelper INSTANCE = new ResponseErrorHelper();
  
  private ResponseErrorHelper() {}

  public ResponseError generateResponseError(FieldError e) {
    ResponseError error = new ResponseError();
    error.setErrorCode(e.getCode());
    error.setErrorMessage(e.getDefaultMessage());
    error.setLocalizedErrorMessage(e.getDefaultMessage());
    return error;
  }

  public ResponseError generateResponseError(ObjectError e) {
    ResponseError error = new ResponseError();
    error.setErrorCode(e.getCode());
    error.setErrorMessage(e.getDefaultMessage());
    error.setLocalizedErrorMessage(e.getDefaultMessage());
    return error;
  }
}
