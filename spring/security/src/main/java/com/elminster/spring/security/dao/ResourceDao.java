package com.elminster.spring.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elminster.spring.security.domain.Resource;

@Repository
public interface ResourceDao extends JpaRepository<Resource, Integer> {

}
