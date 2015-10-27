package com.elminster.spring.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elminster.common.util.Assert;
import com.elminster.common.util.CollectionUtil;
import com.elminster.spring.security.dao.AuthorityResourceDao;
import com.elminster.spring.security.dao.RoleDao;
import com.elminster.spring.security.dao.UserDao;
import com.elminster.spring.security.domain.AuthorityResource;
import com.elminster.spring.security.domain.Role;
import com.elminster.spring.security.domain.User;

@Service
public class AuthUserServiceImpl implements AuthUserService {
  
  private final UserDao userDao;
  private final RoleDao roleDao;
  private AuthorityResourceDao authDao;
  
  @Autowired
  public AuthUserServiceImpl(UserDao userDao, RoleDao roleDao, AuthorityResourceDao authDao) {
    this.userDao = userDao;
    this.roleDao = roleDao;
    this.authDao = authDao;
  }

  @Override
  public User findUserByUsername(String username) {
    return userDao.findByUsername(username);
  }

  @Override
  public User findUserById(int userId) {
    return userDao.findOne(userId);
  }

  @Override
  public User addUser(User user) {
    Assert.notNull(user, "User cannot be null.");
    Assert.notEmpty(user.getUsername(), "User's username cannot be empty.");
    Assert.notEmpty(user.getPassword(), "User's password cannot be empty.");
    Assert.notEmpty(user.getEmail(), "User's email canot be empty.");
    if (CollectionUtil.isEmpty(user.getRoles())) {
      // add default role
      Role userRole = roleDao.findByName("User");
      user.addRole(userRole);
    }
    return userDao.save(user);
  }

  @Override
  public void removeUser(User user) {
    userDao.delete(user);
  }

  @Override
  public User saveUser(User user) {
    return userDao.save(user);
  }

  @Override
  public boolean isUsernameExists(String username) {
    return userDao.existsByUsername(username);
  }

  @Override
  public User addRole(User user, String roleName) {
    Role role = roleDao.findByName(roleName);
    user.addRole(role);
    return userDao.save(user);
  }

  @Override
  public User removeRole(User user, String roleName) {
    Role role = roleDao.findByName(roleName);
    user.removeRole(role);
    return userDao.save(user);
  }

  @Override
  public User addAuthority(User user, String authName) {
    AuthorityResource authority = authDao.findByName(authName);
    user.addAuthorities(authority);
    return userDao.save(user);
  }

  @Override
  public User removeAuthority(User user, String authName) {
    AuthorityResource authority = authDao.findByName(authName);
    user.removeAuthorities(authority);
    return userDao.save(user);
  }
  
}
