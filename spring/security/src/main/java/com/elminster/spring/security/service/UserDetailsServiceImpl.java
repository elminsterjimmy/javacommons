package com.elminster.spring.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elminster.spring.security.domain.User;
import com.elminster.spring.security.model.UserDetailsImpl;

/**
 * The default user details service implementation. Extending this service to create more detail service for you own
 * user details.
 * 
 * @author jgu
 * @version 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private AuthUserService userService;

  public UserDetailsServiceImpl() {
  }

  /**
   * @param userService
   *          the user service to set
   */
  @Autowired
  public void setDefaultUserDetailsDao(AuthUserService userService) {
    this.userService = userService;
  }

  @Transactional
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserDetails userDetails = null;
    try {
      User user = userService.findUserByUsername(username);
      if (null != user) {
        userDetails = new UserDetailsImpl(user);
      }
    } catch (Exception e) {
      throw new UsernameNotFoundException("fetch user failed.", e);
    }
    return userDetails;
  }
}
