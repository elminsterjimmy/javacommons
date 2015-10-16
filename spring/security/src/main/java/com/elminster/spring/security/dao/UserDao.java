package com.elminster.spring.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.elminster.spring.security.domain.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
  
  public User findByUsername(String username);
  
  @Query("select count(user) > 0 from User user where user.username = ?1")
  public boolean existsByUsername(String username);
}
