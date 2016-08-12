package com.elminster.spring.web.helper;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.elminster.web.commons.response.JsonResponse;

public class BindingResultHelper {

  public static final BindingResultHelper INSTANCE = new BindingResultHelper();
  private static final ResponseErrorHelper responseErrorHelper = ResponseErrorHelper.INSTANCE;

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

  public JsonResponse buildErrorJsonResponse(BindingResult bindingResult) {
    JsonResponse jsonResponse = new JsonResponse();
    jsonResponse.setStatus(JsonResponse.STATUS_ERROR);
    for (Object object : bindingResult.getAllErrors()) {
      if (object instanceof FieldError) {
        FieldError fieldError = (FieldError) object;
        jsonResponse.addError(responseErrorHelper.generateResponseError(fieldError));
      }

      if (object instanceof ObjectError) {
        ObjectError objectError = (ObjectError) object;
        jsonResponse.addError(responseErrorHelper.generateResponseError(objectError));
      }
    }
    return jsonResponse;
  }
}
