package com.elminster.spring.security.endpoint.error;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.elminster.web.commons.response.JsonResponseBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The 401 unauthorized entry point.
 * 
 * @author jgu
 * @version 1.0
 */
@Component
public class Http401UnauthorizedEntryPoint implements AuthenticationEntryPoint {
  /** the logger. */
  private static final Log logger = LogFactory.getLog(Http401UnauthorizedEntryPoint.class);

  /**
   * Always returns a 401 error code to the client.
   */
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {
    logger.error(String.format("Pre-authenticated entry point [%s] called. Rejecting access.", request.getPathTranslated()));
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    
    httpResponse.setContentType(MediaType.APPLICATION_JSON.toString());
    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = mapper.writeValueAsString(JsonResponseBuilder.INSTANCE.buildErrorJsonResponse(exception));
    httpResponse.getOutputStream().print(jsonInString);
  }

}
