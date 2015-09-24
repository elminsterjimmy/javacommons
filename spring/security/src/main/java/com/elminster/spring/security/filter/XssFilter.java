package com.elminster.spring.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * HtmlEncode simply does NOT cover all XSS attacks. (Need to be implemented.)
 * 
 * @author jgu
 * @version 1.0
 */
public class XssFilter implements Filter {

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
      ServletException {
    // TODO Need to be implemented
    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void destroy() {
  }
}
