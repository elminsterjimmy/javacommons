package com.elminster.spring.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elminster.spring.security.domain.Authority;

@Repository
public interface AuthorityDao extends JpaRepository<Authority, Integer> {

}