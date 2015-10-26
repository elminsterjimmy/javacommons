package com.elminster.spring.security.filter;

import java.io.IOException;
import java.security.SecureRandom;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.elminster.web.commons.response.JsonResponseBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The CSRF token filter, that will use cookie to store the CSRF token.
 * Check if the CSRF token equals to the one in header.
 * The client side should generate a random CSRF token in its header and put it
 * to the cookie.
 * Considering a website is only allowed to read/write a Cookie for its own domain,
 * only the real site can send the same value in both headers.
 * 
 * @author jgu
 * @version 1.0
 */
public class CsrfTokenFilter extends OncePerRequestFilter {

  /** the CSRF token name in cookie. */
  public static final String CSRF_TOKEN = "CSRF-TOKEN";
  /** the CSRF token name in header. */
  public static final String X_CSRF_TOKEN = "X-CSRF-TOKEN";
  /** the request matcher. */
  private static final RequestMatcher requireCsrfProtectionMatcher = new DefaultCsrfProtectionMatcher();
  /** random cookie generator. */
  private SecureRandom random = new SecureRandom();

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    if (requireCsrfProtectionMatcher.matches(request)) {
      final Cookie[] cookies = request.getCookies();
      
      String csrfCookieValue = null;
      if (null != cookies) {
        for (Cookie cookie : cookies) {
          if (CSRF_TOKEN.equals(cookie.getName())) {
            csrfCookieValue = cookie.getValue();
          }
        }
      }
      
      if (null == csrfCookieValue) {
        // OK, first request.
        // generate new cookie
        Cookie cookie = new Cookie(CSRF_TOKEN, String.valueOf(random.nextLong()));
        cookie.setHttpOnly(false);
        response.addCookie(cookie);
      } else {
        final String csrfTokenValue = request.getHeader(X_CSRF_TOKEN);
        if (!csrfCookieValue.equals(csrfTokenValue)) {
          Exception e = new AccessDeniedException("Missing or non-matching CSRF-token");
          response.setContentType("application/json");
          response.setStatus(HttpServletResponse.SC_FORBIDDEN);
          ObjectMapper mapper = new ObjectMapper();
          String jsonInString = mapper.writeValueAsString(JsonResponseBuilder.INSTANCE.buildErrorJsonResponse(e));
          response.getOutputStream().print(jsonInString);
          return;
        }
      }
    }
    filterChain.doFilter(request, response);
  }
}
