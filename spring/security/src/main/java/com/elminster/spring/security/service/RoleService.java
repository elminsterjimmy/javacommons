package com.elminster.spring.security.service;

import com.elminster.spring.security.domain.Role;

public interface RoleService {
  
  public Role addRole(Role role);
  
  public void removeRole(Role role);
  
  public Role saveRole(Role role);

  public Role findByRoleName(String roleName);
  
  public Role addAuth(Role role, String authName);
  
  public Role removeAuth(Role role, String authName);
}
