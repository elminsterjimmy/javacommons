package com.elminster.spring.web.helper;

import com.elminster.web.commons.response.ResponseError;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.elminster.web.commons.response.JsonResponse;

/**
 * The helper to convert validation {@link BindingResult} to {@link JsonResponse}.
 *
 * @author jgu
 * @version 1.0
 */
public class BindingResultHelper {

  /** the helper instance. */
  public static final BindingResultHelper INSTANCE = new BindingResultHelper();

  private BindingResultHelper() {
  }

  public String buildErrorString(BindingResult bindingResult) {
    StringBuilder sb = new StringBuilder();
    for (Object object : bindingResult.getAllErrors()) {
      if (object instanceof FieldError) {
        FieldError fieldError = (FieldError) object;
        System.out.println(fieldError.getCode());
      }

      if (object instanceof ObjectError) {
        ObjectError objectError = (ObjectError) object;
        System.out.println(objectError.getCode());
      }
    }
    return sb.toString();
  }

  /**
   * Build the error {@link JsonResponse} from {@link BindingResult}.
   * @param bindingResult the BindingResult
   * @return the error JsonResponse
   */
  public JsonResponse buildErrorJsonResponse(BindingResult bindingResult) {
    JsonResponse jsonResponse = new JsonResponse();
    jsonResponse.setStatus(JsonResponse.STATUS_ERROR);
    for (Object object : bindingResult.getAllErrors()) {
      if (object instanceof FieldError) {
        FieldError fieldError = (FieldError) object;
        jsonResponse.addError(generateResponseError(fieldError));
      }

      if (object instanceof ObjectError) {
        ObjectError objectError = (ObjectError) object;
        jsonResponse.addError(generateResponseError(objectError));
      }
    }
    return jsonResponse;
  }

  /**
   * Convert {@link FieldError} to {@link ResponseError}
   * @param fieldError the field error
   * @return the response error
   */
  private ResponseError generateResponseError(FieldError fieldError) {
    ResponseError error = new ResponseError();
    error.setErrorCode(fieldError.getCode());
    error.setErrorMessage(fieldError.getDefaultMessage());
    error.setLocalizedErrorMessage(fieldError.getDefaultMessage());
    return error;
  }

  /**
   * Convert {@link FieldError} to {@link ObjectError}
   * @param objectError the object error
   * @return the response error
   */
  private ResponseError generateResponseError(ObjectError objectError) {
    ResponseError error = new ResponseError();
    error.setErrorCode(objectError.getCode());
    error.setErrorMessage(objectError.getDefaultMessage());
    error.setLocalizedErrorMessage(objectError.getDefaultMessage());
    return error;
  }
}
