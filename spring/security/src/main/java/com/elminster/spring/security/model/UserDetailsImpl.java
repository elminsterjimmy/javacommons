package com.elminster.spring.security.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.elminster.common.util.CollectionUtil;
import com.elminster.spring.security.domain.AuthorityResource;
import com.elminster.spring.security.domain.Role;
import com.elminster.spring.security.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The default user details.
 * Attention: to extend this user details, please notice the 
 * Json serialize and deserialize.
 * 
 * @author jgu
 * @version 1.0
 */
public class UserDetailsImpl implements UserDetails {

  /**
   * 
   */
  private static final long serialVersionUID = 3423421620227786293L;
  
  private final User user;
  private final Collection<? extends GrantedAuthority> grantedAuthorities;
  
  public UserDetailsImpl(User user) {
    this.user = user;
    List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
    List<AuthorityResource> authRes = new ArrayList<>();
    if (null != user.getAuthorities()) {
      authRes.addAll(user.getAuthorities());
    }
    List<Role> roles = user.getRoles();
    if (CollectionUtil.isNotEmpty(roles)) {
      for (Role role : roles) {
        if (null != role.getAuthorities()) {
          authRes.addAll(role.getAuthorities());
        }
      }
    }
    for (AuthorityResource ar : authRes) {
      GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(ar.getName());
      auths.add(grantedAuthority);
    }
    grantedAuthorities = Collections.unmodifiableList(auths);
  }

  @JsonIgnore
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return grantedAuthorities;
  }

  @JsonIgnore
  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @JsonIgnore
  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isEnabled() {
    return user.isEnable();
  }
  
  /**
   * @return the user
   */
  public User getUser() {
    return user;
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((user == null) ? 0 : user.hashCode());
    return result;
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    UserDetailsImpl other = (UserDetailsImpl) obj;
    if (user == null) {
      if (other.user != null)
        return false;
    } else if (!user.equals(other.user))
      return false;
    return true;
  }
}
