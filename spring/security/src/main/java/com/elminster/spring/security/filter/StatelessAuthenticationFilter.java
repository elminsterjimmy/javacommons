package com.elminster.spring.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.stereotype.Component;

import com.elminster.spring.security.service.TokenAuthenticationService;

/**
 * The filter that uses token to retrieve the user info.
 * 
 * @author jgu
 * @version 1.0
 */
@Component
public class StatelessAuthenticationFilter implements Filter {

  /** the logger. */
  private static final Log logger = LogFactory.getLog(StatelessAuthenticationFilter.class);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
      ServletException {
    if (null == SecurityContextHolder.getContext().getAuthentication()) {
      try {
        Authentication authentication = tokenAuthenticationService
            .getAuthenticationFromRequest((HttpServletRequest) request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch (Exception e) {
        if (logger.isDebugEnabled()) {
          logger.debug("request authentication filter failed.", e);
        }
        accessDeniedHandler.handle((HttpServletRequest) request, (HttpServletResponse) response,
            new AccessDeniedException("request authentication failed.", e));
        return;
      }
    }
    chain.doFilter(request, response); // always continue
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void destroy() {
  }

}
