package com.elminster.spring.security.filter;

import java.io.IOException;

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
  /** the access denied handler. */
  private AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandlerImpl();

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    if (requireCsrfProtectionMatcher.matches(request)) {
      final String csrfTokenValue = request.getHeader(X_CSRF_TOKEN);
      final Cookie[] cookies = request.getCookies();
      
      String csrfCookieValue = null;
      if (null != cookies) {
        for (Cookie cookie : cookies) {
          if (CSRF_TOKEN.equals(cookie.getName())) {
            csrfCookieValue = cookie.getValue();
          }
        }
      }
      
      if (null == csrfTokenValue || !csrfTokenValue.equals(csrfCookieValue)) {
        accessDeniedHandler.handle(request, response, new AccessDeniedException("Missing or non-matching CSRF-token"));
        return;
      }
    }
    filterChain.doFilter(request, response);
  }
}
