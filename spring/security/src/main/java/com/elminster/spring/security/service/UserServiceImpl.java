package com.elminster.spring.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elminster.spring.security.dao.UserDao;
import com.elminster.spring.security.domain.User;

@Service
public class UserServiceImpl implements UserService {
  
  @Autowired
  private UserDao userDao;
  
  public UserServiceImpl() {
  }
  
  /**
   * @return the userDao
   */
  public UserDao getUserDao() {
    return userDao;
  }

  /**
   * @param userDao the userDao to set
   */
  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public User findUserByUsername(String username) {
    return userDao.findByUsername(username);
  }

  @Override
  public User findUserById(int id) {
    return userDao.findOne(id);
  }

  @Override
  public User addUser(String username, String password, String email) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void removeUser(int userId) {
    userDao.delete(userId);
  }

  @Override
  public User changeUserStatue(int userId, int status) {
    // TODO Auto-generated method stub
    return null;
  }
}
