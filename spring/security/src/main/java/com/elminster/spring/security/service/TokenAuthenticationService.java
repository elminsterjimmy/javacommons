package com.elminster.spring.security.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.elminster.common.util.DateUtil;
import com.elminster.common.util.EncryptUtil;
import com.elminster.spring.security.dao.CookieUserDao;
import com.elminster.spring.security.domain.CookieUser;
import com.elminster.spring.security.domain.User;
import com.elminster.spring.security.exception.TokenGenerateException;
import com.elminster.spring.security.model.UserDetailsImpl;

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
  /** the default expiration period. */
  private static final long DEFAULT_EXPIRATION_PERIOD = 3 * DateUtil.DAY;
  /** the token helper. */
  @Autowired
  private CookieUserDao cookieUserDao;
  /** the expiration period. */
  private long expirationPeriod = DEFAULT_EXPIRATION_PERIOD;

  public TokenAuthenticationService() {
  }
  
  /**
   * @return the cookieUserDao
   */
  public CookieUserDao getCookieUserDao() {
    return cookieUserDao;
  }

  /**
   * @param cookieUserDao the cookieUserDao to set
   */
  public void setCookieUserDao(CookieUserDao cookieUserDao) {
    this.cookieUserDao = cookieUserDao;
  }

  /**
   * Add authentication to response header.
   * 
   * @param response
   *          the response
   * @param authentication
   *          the authentication
   */
  public void addAuthentication2Header(HttpServletResponse response, Authentication authentication) throws Exception {
    final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getDetails();
    response.addHeader(AUTH_HEADER_NAME, generateToken4User(userDetails.getUser()));
  }

  /**
   * Get the authentication from request.
   * 
   * @param request
   *          the request
   * @return the authentication
   */
  @Transactional
  public Authentication getAuthenticationFromRequest(HttpServletRequest request) throws Exception {
    Authentication authentication = null;
    final String token = request.getHeader(AUTH_HEADER_NAME);
    if (null != token) {
      final CookieUser cookieUser = cookieUserDao.findByCookie(token);
      if (null != cookieUser) {
        Date lastLogin = cookieUser.getLastLogin();
        long current = System.currentTimeMillis();
        if (current - lastLogin.getTime() > expirationPeriod) {
          cookieUserDao.delete(cookieUser);
        } else {
          // update last login date
          cookieUser.setLastLogin(new Date(current));
          cookieUserDao.save(cookieUser);
          
          User user = cookieUser.getUser();
          UserDetails userDetails = new UserDetailsImpl(user);
          authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
              userDetails.getPassword(), userDetails.getAuthorities());
          ((UsernamePasswordAuthenticationToken) authentication).setDetails(userDetails);
        }
      }
    }
    return authentication;
  }

  private String generateToken4User(User user) throws TokenGenerateException {
    try {
      String token = EncryptUtil.encryptSHA512((user.toString() + System.currentTimeMillis()).getBytes());
      CookieUser cookieUser = new CookieUser();
      cookieUser.setCookie(token);
      cookieUser.setUser(user);
      cookieUser.setIpaddr(user.getLastLoginIp());
      cookieUser.setLastLogin(user.getLastLoginDate());
      cookieUserDao.save(cookieUser);
      return token;
    } catch (Exception e) {
      throw new TokenGenerateException(e);
    }
  }

  /**
   * @return the expirationPeriod
   */
  public long getExpirationPeriod() {
    return expirationPeriod;
  }

  /**
   * @param expirationPeriod the expirationPeriod to set
   */
  public void setExpirationPeriod(long expirationPeriod) {
    this.expirationPeriod = expirationPeriod;
  }
}
