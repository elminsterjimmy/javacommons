package com.elminster.spring.security.filter;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

/**
 * The default CSRF protection matcher.
 * Only allow Get/Head/Trace/Options methods go through without CSRF token.
 * 
 * @author jgu
 * @version 1.0
 */
@Service
public class DefaultCsrfProtectionMatcher implements RequestMatcher {
  /** the allow methods. */
  private final Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean matches(HttpServletRequest request) {
    return !allowedMethods.matcher(request.getMethod()).matches();
  }

}
