package com.elminster.spring.security.service;

import com.elminster.spring.security.domain.User;

public interface UserService {

  public User findUserByUsername(String username);
  
  public User findUserById(int id);
  
  public User addUser(String username, String password, String email);
  
  public void removeUser(int userId);
  
  public User changeUserStatue(int userId, int status);
}
