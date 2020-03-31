package com.elminster.spring.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.elminster.spring.web.helper.BindingResultHelper;
import com.elminster.web.commons.response.JsonResponse;
import com.elminster.web.commons.response.JsonResponseBuilder;

/**
 * The Global Controller exception handler.
 * 
 * @author jgu
 * @version 1.0
 */
@ControllerAdvice
final public class GlobalControllerExceptionHandler {

  /** the logger. */
  private static final Log logger = LogFactory.getLog(GlobalControllerExceptionHandler.class);
  /** the JSON response builder. */
  private static JsonResponseBuilder jsonResponseBuilder = JsonResponseBuilder.INSTANCE;

  /**
   * Handle validate exception.
   * @param exception the MethodArgumentNotValidException exception
   * @return a response with HTTP <code>400</code> error.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  public ResponseEntity<JsonResponse> handleValidateException(MethodArgumentNotValidException exception) {
    logger.error("Validate failed. Details: " + exception.getLocalizedMessage());
    return new ResponseEntity<>(BindingResultHelper.INSTANCE.buildErrorJsonResponse(exception
        .getBindingResult()), HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle uncaught exception.
   * @param exception the uncaught exception
   * @return a response with HTTP <code>500</code> error.
   */
  @ExceptionHandler(Exception.class)
  @ResponseBody
  public ResponseEntity<JsonResponse> handleUncaughtException(Exception exception) {
    logger.error("Internet server error. Details: " + exception.getLocalizedMessage());
    return new ResponseEntity<>(jsonResponseBuilder.buildErrorJsonResponse(exception),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
