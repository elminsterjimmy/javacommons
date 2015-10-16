package com.elminster.spring.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elminster.spring.security.domain.Role;

@Repository
public interface RoleDao extends JpaRepository<Role, Integer> {

  Role findByName(String roleName);
}
