package com.elminster.spring.security.service;

import com.elminster.spring.security.domain.User;

public interface UserService {

  public User findUserByUsername(String username);
  
  public User findUserById(int userId);
  
  public User addUser(User user);
  
  public void removeUser(User user);
  
  public User saveUser(User user);
  
  public boolean isUsernameExists(String username);
  
  public User addRole(User user, String roleName);
  
  public User removeRole(User user, String roleName);
  
  public User addAuthority(User user, String authName);
  
  public User removeAuthority(User user, String authName);
}
