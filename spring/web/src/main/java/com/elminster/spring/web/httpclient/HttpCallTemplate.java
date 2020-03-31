package com.elminster.spring.web.httpclient;

import com.elminster.common.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.StringJoiner;

/**
 * The HTTP call template.
 *
 * @author jgu
 * @version 1.0
 */
public class HttpCallTemplate {

  /***/
  private static final String HTTP_CALL_DEBUG_REQUEST = "Invoking HTTP [{}] with header [{}] and body [{}], uriVariables [{}]...";
  /***/
  private static final String HTTP_CALL_DEBUG_RESPONSE = "Invoking HTTP [{}] with header [{}] and body [{}], uriVariables [{}], returns HTTP state=[{}], header=[{}], body=[{}].";
  /***/
  private static final String HTTPCALL_EXCEPTION_TEMPLATE = "failed to call HTTP [%s] with header [%s] and body [%s], uriVariables [%s].";
  /***/
  private static final String HTTPCALL_RESPONSE_EXCEPTION_TEMPLATE = "failed to call HTTP [%s] with header [%s] and body [%s], uriVariables [%s], returns HTTP state=[%s], header=[%s], body=[%s].";
  /***/
  private static final Logger logger = LoggerFactory.getLogger(HttpCallTemplate.class);

  /**
   * Make a HTTP web call and get the response.
   *
   * @param restTemplate
   *     the RestTemplate
   * @param url
   *     the URL
   * @param method
   *     the HTTP method
   * @param requestBody
   *     the request body
   * @param responseType
   *     the response body type
   * @param uriVariables
   *     the URI variables
   * @param <T>
   *     the response body type
   * @param <K>
   *     the request body type
   * @return the response
   * @throws HttpCallException
   *     on error
   */
  public static <T, K> ResponseEntity<T> httpCall(RestTemplate restTemplate,
                                                  String url, HttpMethod method,
                                                  K requestBody, Class<T> responseType,
                                                  Object... uriVariables) throws HttpCallException {
    HttpEntity<K> request = createHttpRequestEntity(requestBody);
    if (logger.isDebugEnabled()) {
      logger.debug(HTTP_CALL_DEBUG_REQUEST, url, request.getHeaders(), request.getBody(), extractUriVariables(uriVariables));
    }
    try {
      ResponseEntity<T> response = restTemplate.exchange(url, method, request, responseType, uriVariables);
      if (logger.isDebugEnabled()) {
        logger.debug(HTTP_CALL_DEBUG_RESPONSE, url,
            request.getHeaders(), request.getBody(), extractUriVariables(uriVariables),
            response.getStatusCode(), response.getHeaders(), response.getBody());
      }
      return response;
    } catch (RestClientException ex) {
      return handleRestClientException(ex, url, request, responseType, uriVariables);
    }
  }

  /**
   * Make a HTTP web call and get the response with a list of entities.
   *
   * @param restTemplate
   *     the RestTemplate
   * @param url
   *     the URL
   * @param method
   *     the HTTP method
   * @param requestBody
   *     the request body
   * @param parameterizedTypeReference
   *     the response body type
   * @param uriVariables
   *     the URI variables
   * @param <T>
   *     the response body type
   * @param <K>
   *     the request body type
   * @return the response
   * @throws HttpCallException
   *     on error
   */
  public static <T, K> ResponseEntity<T> httpCall(RestTemplate restTemplate, String url, HttpMethod method,
                                                  K requestBody, ParameterizedTypeReference<T> parameterizedTypeReference,
                                                  Object... uriVariables) throws HttpCallException {
    HttpEntity<K> request = createHttpRequestEntity(requestBody);
    try {
      ResponseEntity<T> response = restTemplate.exchange(url, method, request, parameterizedTypeReference, uriVariables);
      if (logger.isDebugEnabled()) {
        logger.debug(HTTP_CALL_DEBUG_RESPONSE, url,
            request.getHeaders(), request.getBody(), extractUriVariables(uriVariables),
            response.getStatusCode(), response.getHeaders(), response.getBody());
      }
      return response;
    } catch (RestClientException ex) {
      return handleRestClientException(ex, url, request, parameterizedTypeReference, uriVariables);
    }
  }

  /**
   * Handle the {@link org.springframework.web.client.RestClientException} from {@link org.springframework.web.client.RestTemplate}.
   *
   * @param ex
   *     the RestClientException
   * @param url
   *     the request URL
   * @param request
   *     the request
   * @param responseType
   *     *     the response body type
   * @param uriVariables
   *     the request URI variables
   * @param <T>
   *     the response type
   * @param <K>
   *     the request type
   * @return the ResponseEntity
   */
  private static <T, K> ResponseEntity<T> handleRestClientException(RestClientException ex, String url,
                                                                    HttpEntity<K> request,
                                                                    Class<T> responseType,
                                                                    Object... uriVariables) {
    if (ex instanceof RestClientResponseException) {
      RestClientResponseException restClientResponseException = (RestClientResponseException) ex;
      ResponseEntity response = createResponseEntity(restClientResponseException);
      return handleHttpResponse(url, request, response, responseType, uriVariables);
    } else {
      String message = String.format(HTTPCALL_EXCEPTION_TEMPLATE, url, request.getHeaders(), request.getBody(),
          extractUriVariables(uriVariables));
      throw new HttpCallException(message, ex);
    }
  }

  private static ResponseEntity createResponseEntity(RestClientResponseException restClientResponseException) {
    return ResponseEntity
        .status(restClientResponseException.getRawStatusCode())
        .headers(restClientResponseException.getResponseHeaders())
        .body(restClientResponseException.getResponseBodyAsString());
  }

  /**
   * Handle the {@link org.springframework.web.client.RestClientException} from {@link org.springframework.web.client.RestTemplate}.
   *
   * @param ex
   *     the RestClientException
   * @param url
   *     the request URL
   * @param request
   *     the request
   * @param parameterizedTypeReference
   *     *     the response body type
   * @param uriVariables
   *     the request URI variables
   * @param <T>
   *     the response type
   * @param <K>
   *     the request type
   * @return the ResponseEntity
   */
  private static <T, K> ResponseEntity<T> handleRestClientException(RestClientException ex, String url,
                                                                    HttpEntity<K> request,
                                                                    ParameterizedTypeReference<T> parameterizedTypeReference,
                                                                    Object... uriVariables) {
    if (ex instanceof RestClientResponseException) {
      RestClientResponseException restClientResponseException = (RestClientResponseException) ex;
      ResponseEntity response = createResponseEntity(restClientResponseException);
      return handleHttpResponse(url, request, response, parameterizedTypeReference, uriVariables);
    } else {
      String message = String.format(HTTPCALL_EXCEPTION_TEMPLATE, url, request.getHeaders(), request.getBody(), extractUriVariables(uriVariables));
      throw new HttpCallException(message, ex);
    }
  }

  /**
   * Make a HTTP POST and get the response.
   *
   * @param restTemplate
   *     the RestTemplate
   * @param url
   *     the URL
   * @param requestBody
   *     the request body
   * @param responseType
   *     the response body type
   * @param uriVariables
   *     the URI variables
   * @param <T>
   *     the response body type
   * @param <K>
   *     the request body type
   * @return the response
   * @throws HttpCallException
   *     on error
   */
  public static <T, K> ResponseEntity<T> httpPost(RestTemplate restTemplate, String url, K requestBody, Class<T> responseType, Object... uriVariables) throws HttpCallException {
    return httpCall(restTemplate, url, HttpMethod.POST, requestBody, responseType, uriVariables);
  }

  /**
   * Make a HTTP POST and get the response with a list of entities.
   *
   * @param restTemplate
   *     the RestTemplate
   * @param url
   *     the URL
   * @param requestBody
   *     the request body
   * @param responseType
   *     the response body type
   * @param uriVariables
   *     the URI variables
   * @param <T>
   *     the response body type
   * @param <K>
   *     the request body type
   * @return the response
   * @throws HttpCallException
   *     on error
   */
  public static <T, K> ResponseEntity<T> httpPost(RestTemplate restTemplate, String url, K requestBody,
                                                  ParameterizedTypeReference<T> responseType,
                                                  Object... uriVariables) throws HttpCallException {
    return httpCall(restTemplate, url, HttpMethod.POST, requestBody, responseType, uriVariables);
  }

  /**
   * Make a HTTP GET and get the response.
   *
   * @param restTemplate
   *     the RestTemplate
   * @param url
   *     the URL
   * @param requestBody
   *     the request body
   * @param responseType
   *     the response body type
   * @param uriVariables
   *     the URI variables
   * @param <T>
   *     the response body type
   * @param <K>
   *     the request body type
   * @return the response
   * @throws HttpCallException
   *     on error
   */
  public static <T, K> ResponseEntity<T> httpGet(RestTemplate restTemplate, String url, K requestBody, Class<T> responseType, Object... uriVariables) throws HttpCallException {
    return httpCall(restTemplate, url, HttpMethod.GET, requestBody, responseType, uriVariables);
  }

  /**
   * Make a HTTP GET and get the response with a list of entities.
   *
   * @param restTemplate
   *     the RestTemplate
   * @param url
   *     the URL
   * @param requestBody
   *     the request body
   * @param responseType
   *     the response body type
   * @param uriVariables
   *     the URI variables
   * @param <T>
   *     the response body type
   * @param <K>
   *     the request body type
   * @return the response
   * @throws HttpCallException
   *     on error
   */
  public static <T, K> ResponseEntity<T> httpGet(RestTemplate restTemplate, String url, K requestBody, ParameterizedTypeReference<T> responseType, Object... uriVariables) throws HttpCallException {
    return httpCall(restTemplate, url, HttpMethod.GET, requestBody, responseType, uriVariables);
  }

  /**
   * Make a HTTP DELETE and get the response.
   *
   * @param restTemplate
   *     the RestTemplate
   * @param url
   *     the URL
   * @param requestBody
   *     the request body
   * @param responseType
   *     the response body type
   * @param uriVariables
   *     the URI variables
   * @param <T>
   *     the response body type
   * @param <K>
   *     the request body type
   * @return the response
   * @throws HttpCallException
   *     on error
   */
  public static <T, K> ResponseEntity<T> httpDelete(RestTemplate restTemplate, String url, K requestBody, Class<T> responseType, Object... uriVariables) throws HttpCallException {
    return httpCall(restTemplate, url, HttpMethod.DELETE, requestBody, responseType, uriVariables);
  }

  /**
   * Make a HTTP PUT and get the response.
   *
   * @param restTemplate
   *     the RestTemplate
   * @param url
   *     the URL
   * @param requestBody
   *     the request body
   * @param responseType
   *     the response body type
   * @param uriVariables
   *     the URI variables
   * @param <T>
   *     the response body type
   * @param <K>
   *     the request body type
   * @return the response
   * @throws HttpCallException
   *     on error
   */
  public static <T, K> ResponseEntity<T> httpPut(RestTemplate restTemplate, String url, K requestBody, Class<T> responseType, Object... uriVariables) throws HttpCallException {
    return httpCall(restTemplate, url, HttpMethod.PUT, requestBody, responseType, uriVariables);
  }

  /**
   * Make a HTTP PATCH and get the response.
   *
   * @param restTemplate
   *     the RestTemplate
   * @param url
   *     the URL
   * @param requestBody
   *     the request body
   * @param responseType
   *     the response body type
   * @param uriVariables
   *     the URI variables
   * @param <T>
   *     the response body type
   * @param <K>
   *     the request body type
   * @return the response
   * @throws HttpCallException
   *     on error
   */
  public static <T, K> ResponseEntity<T> httpPatch(RestTemplate restTemplate, String url, K requestBody, Class<T> responseType, Object... uriVariables) throws HttpCallException {
    return httpCall(restTemplate, url, HttpMethod.PATCH, requestBody, responseType, uriVariables);
  }

  /**
   * Handle the HTTP response
   *
   * @param url
   *     the URL
   * @param request
   *     the request
   * @param response
   *     the response
   * @param uriVariables
   *     the URI variable
   * @param <T>
   *     the response type
   * @param <K>
   *     the request type
   * @return the HTTP response
   */
  private static <T, K> ResponseEntity<T> handleHttpResponse(String url, HttpEntity<K> request,
                                                             ResponseEntity<T> response,
                                                             ParameterizedTypeReference<T> parameterizedTypeReference,
                                                             Object... uriVariables) {
    if (logger.isDebugEnabled()) {
      logger.debug(HTTP_CALL_DEBUG_RESPONSE, url,
          request.getHeaders(), request.getBody(), extractUriVariables(uriVariables),
          response.getStatusCode(), response.getHeaders(), response.getBody());
    }
    HttpStatus httpStatus = response.getStatusCode();
    if (HttpStatus.INTERNAL_SERVER_ERROR == httpStatus) {
      thorwUnexpectedHttpStatusException(url, request, response, parameterizedTypeReference, uriVariables);
    }
    if (!httpStatus.is2xxSuccessful()) {
      thorwUnexpectedHttpStatusException(url, request, response, parameterizedTypeReference, uriVariables);
    }
    return response;
  }

  /**
   * Handle the HTTP response
   *
   * @param url
   *     the URL
   * @param request
   *     the request
   * @param response
   *     the response
   * @param uriVariables
   *     the URI variable
   * @param <T>
   *     the response type
   * @param <K>
   *     the request type
   * @return the HTTP response
   */
  private static <T, K> ResponseEntity<T> handleHttpResponse(String url, HttpEntity<K> request,
                                                             ResponseEntity<T> response,
                                                             Class<T> responseType,
                                                             Object... uriVariables) {
    if (logger.isDebugEnabled()) {
      logger.debug(HTTP_CALL_DEBUG_RESPONSE, url,
          request.getHeaders(), request.getBody(), extractUriVariables(uriVariables),
          response.getStatusCode(), response.getHeaders(), response.getBody());
    }
    HttpStatus httpStatus = response.getStatusCode();
    if (HttpStatus.INTERNAL_SERVER_ERROR == httpStatus) {
      thorwUnexpectedHttpStatusException(url, request, response, responseType, uriVariables);
    }
    if (!httpStatus.is2xxSuccessful()) {
      thorwUnexpectedHttpStatusException(url, request, response, responseType, uriVariables);
    }
    return response;
  }

  /**
   * Throw the unexpected HTTP status exception with detailed message.
   *
   * @param url
   *     the URL of the HTTP call
   * @param request
   *     the request of the HTTP call
   * @param response
   *     the respone of the HTTP call
   * @param uriVariables
   *     the RUI variables of the HTTP call
   * @param <T>
   *     the response type
   * @param <K>
   *     the request type
   * @throws UnexpectedHttpStatusException
   *     with detailed message
   */
  private static <T, K> void thorwUnexpectedHttpStatusException(String url, HttpEntity<K> request,
                                                                ResponseEntity<T> response,
                                                                ParameterizedTypeReference<T> parameterizedTypeReference,
                                                                Object... uriVariables) throws UnexpectedHttpStatusException {
    String message = String.format(HTTPCALL_RESPONSE_EXCEPTION_TEMPLATE, url,
        request.getHeaders(), request.getBody(), extractUriVariables(uriVariables),
        response.getStatusCode(), response.getHeaders(), response.getBody());
    throw new UnexpectedHttpStatusException(message, response, parameterizedTypeReference);
  }

  /**
   * Throw the unexpected HTTP status exception with detailed message.
   *
   * @param url
   *     the URL of the HTTP call
   * @param request
   *     the request of the HTTP call
   * @param response
   *     the respone of the HTTP call
   * @param uriVariables
   *     the RUI variables of the HTTP call
   * @param <T>
   *     the response type
   * @param <K>
   *     the request type
   * @throws UnexpectedHttpStatusException
   *     with detailed message
   */
  private static <T, K> void thorwUnexpectedHttpStatusException(String url, HttpEntity<K> request,
                                                                ResponseEntity<T> response,
                                                                Class<T> responseType,
                                                                Object... uriVariables) throws UnexpectedHttpStatusException {
    String message = String.format(HTTPCALL_RESPONSE_EXCEPTION_TEMPLATE, url,
        request.getHeaders(), request.getBody(), extractUriVariables(uriVariables),
        response.getStatusCode(), response.getHeaders(), response.getBody());
    throw new UnexpectedHttpStatusException(message, response, responseType);
  }

  /**
   * Create the HTTP request.
   *
   * @param requestBody
   *     the request body
   * @param <K>
   *     the request type
   * @return the HTTP request
   */
  private static <K> HttpEntity createHttpRequestEntity(K requestBody) {
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    return new HttpEntity<>(requestBody, headers);
  }

  /**
   * Extract the URI variables.
   *
   * @param uriVariables
   *     the URI variables
   * @return the extracted URI variables
   */
  private static String extractUriVariables(Object[] uriVariables) {
    String extractString = Constants.StringConstants.NULL_STRING;
    if (null != uriVariables) {
      StringJoiner stringJoiner = new StringJoiner(Constants.StringConstants.COMMA);
      for (Object var : uriVariables) {
        stringJoiner.add(null == var ? Constants.StringConstants.NULL_STRING : var.toString());
      }
      extractString = stringJoiner.toString();
    }
    return extractString;
  }
}
