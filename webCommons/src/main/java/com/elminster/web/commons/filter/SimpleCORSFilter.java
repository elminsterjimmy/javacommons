package com.elminster.web.commons.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple CORS filter.
 * 
 * @author jgu
 * @version 1.0
 */
public class SimpleCORSFilter implements Filter {

  /**
   * {@inheritDoc}
   */
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    String origin = request.getHeader("Origin");
    String method = request.getMethod();
    
    if (allowOrigin(origin)) {
      HttpServletResponse response = (HttpServletResponse) res;
      response.setHeader("Access-Control-Allow-Origin", origin);
      response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
      response.setHeader("Access-Control-Max-Age", "3600");
      response.setHeader("Access-Control-Allow-Headers",
          "Origin, X-Requested-With, Content-Type, Accept, X-CSRF-TOKEN, X-AUTH-TOKEN");
      response.setHeader("Access-Control-Allow-Credentials", "true");
      response.setHeader("Access-Control-Expose-Headers", "X-CSRF-TOKEN, X-AUTH-TOKEN");
      if (!"OPTIONS".equals(method)) {
        chain.doFilter(req, res);
      }
    }
  }

  /**
   * Filter the allowed origin.
   * 
   * @param origin
   *          the origin to check
   * @return whether the origin is allowed
   */
  protected boolean allowOrigin(String origin) {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  public void init(FilterConfig filterConfig) {
  }

  /**
   * {@inheritDoc}
   */
  public void destroy() {
  }

}