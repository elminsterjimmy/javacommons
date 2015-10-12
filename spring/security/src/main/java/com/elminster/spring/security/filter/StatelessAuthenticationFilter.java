package com.elminster.spring.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.elminster.spring.security.service.TokenAuthenticationService;

/**
 * The filter that uses token to retrieve the user info.
 * Use once per request filter to avoid spring call this filter twice.
 * 
 * @author jgu
 * @version 1.0
 */
@Component
public class StatelessAuthenticationFilter extends OncePerRequestFilter {

  /** the token authentication service. */
  private final TokenAuthenticationService tokenAuthenticationService;
  /** the access denied handler. */
  private AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandlerImpl();

  /**
   * Constructor.
   * 
   * @param tokenAuthenticationService
   */
  @Autowired
  public StatelessAuthenticationFilter(TokenAuthenticationService tokenAuthenticationService) {
    this.tokenAuthenticationService = tokenAuthenticationService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      Authentication authentication = tokenAuthenticationService
          .getAuthenticationFromRequest((HttpServletRequest) request);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (Exception e) {
      accessDeniedHandler.handle((HttpServletRequest) request, (HttpServletResponse) response,
          new AccessDeniedException("request authentication failed.", e));
      return;
    }
  }

}
