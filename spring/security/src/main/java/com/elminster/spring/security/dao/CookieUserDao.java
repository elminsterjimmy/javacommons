package com.elminster.spring.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elminster.spring.security.domain.CookieUser;

@Repository
public interface CookieUserDao extends JpaRepository<CookieUser, Integer> {

  public CookieUser findByCookie(String cookie);
}
