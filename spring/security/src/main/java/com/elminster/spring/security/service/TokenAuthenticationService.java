package com.elminster.spring.security.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.elminster.spring.security.helper.RequestTokenHelper;
import com.elminster.spring.security.model.TokenUserDetails;

/**
 * The token authentication service.
 * 
 * @author jgu
 * @version 1.0
 */
@Service
public class TokenAuthenticationService {

  /** the token header. */
  private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
  /** the token helper. */
  private final RequestTokenHelper tokenHelpler;
  
  /**
   * 
   * @param tokenHelper
   */
  @Autowired
  public TokenAuthenticationService(RequestTokenHelper tokenHelper) {
    this.tokenHelpler = tokenHelper;
  }
  
  /**
   * Add authentication to response header.
   * @param response the response
   * @param authentication the authentication
   */
  public void addAuthentication2Header(HttpServletResponse response, Authentication authentication) {
    final TokenUserDetails user = (TokenUserDetails) authentication.getDetails();
    //user.setExpires(System.currentTimeMillis() + DEFAULT_EXPIRATION);
    response.addHeader(AUTH_HEADER_NAME, tokenHelpler.generateToken4User(user));
  }
  
  /**
   * Get the authentication from request.
   * @param request the request
   * @return the authentication
   */
  public Authentication getAuthenticationFromRequest(HttpServletRequest request) {
    final String token = request.getHeader(AUTH_HEADER_NAME);
    if (null != token) {
      final TokenUserDetails user = tokenHelpler.getUserFromToken(token);
      if (null != user) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            user.getUsername(), user.getPassword(), user.getAuthorities());
        authentication.setDetails(user);
        return authentication;
      }
    }
    return null;
  }
}
