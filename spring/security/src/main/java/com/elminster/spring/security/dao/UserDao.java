package com.elminster.spring.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elminster.spring.security.domain.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
  
  public User findByUsername(String username);
}
