package com.elminster.spring.security.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.elminster.common.util.CollectionUtil;
import com.elminster.common.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The token user details.
 * 
 * @author jgu
 * @version 1.0
 */
public class TokenUserDetails implements UserDetails {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** default expiration: 1 week. */
  private static final long DEFAULT_EXPIRATION = DateUtil.WEEK;

  private final String username;
  private final String password;
  private final Collection<GrantedAuthority> authorities;
  private final boolean accountNonLocked;
  private final boolean enabled;
  private long expiration;
  private boolean alwaysUpdateExpiration;

  public TokenUserDetails(String username, String password, Collection<GrantedAuthority> authorities) {
    this(username, password, authorities, true, true, true);
  }

  public TokenUserDetails(String username, String password, Collection<GrantedAuthority> authorities,
      boolean accountNonLocked, boolean enabled, boolean alwaysUpdateExpiration) {
    super();
    this.password = password;
    this.username = username;
    this.authorities = authorities;
    this.accountNonLocked = accountNonLocked;
    this.enabled = enabled;
    this.alwaysUpdateExpiration = alwaysUpdateExpiration;
    this.expiration = System.currentTimeMillis() + DEFAULT_EXPIRATION;
  }

  @SuppressWarnings("unchecked")
  public TokenUserDetails(Map<String, Object> map) {
    this.username = (String) map.get("username");
    this.password = (String) map.get("password");
    this.accountNonLocked = (Boolean) map.get("accountNonLocked");
    this.enabled = (Boolean) map.get("accountNonLocked");
    this.expiration = (Long) map.get("expiration");
    List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("authorities");
    if (CollectionUtil.isNotEmpty(list)) {
      this.authorities = new ArrayList<GrantedAuthority>(list.size());
      for (Map<String, Object> m : list) {
        authorities.add(new SimpleGrantedAuthority((String) m.get("authority")));
      }
    } else {
      this.authorities = null;
    }
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @JsonIgnore
  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return accountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  /**
   * @return the expiration
   */
  public long getExpiration() {
    return expiration;
  }
  
  /**
   * @return the alwaysUpdateExpiration
   */
  public boolean isAlwaysUpdateExpiration() {
    return alwaysUpdateExpiration;
  }

  /**
   * @param alwaysUpdateExpiration the alwaysUpdateExpiration to set
   */
  public void setAlwaysUpdateExpiration(boolean alwaysUpdateExpiration) {
    this.alwaysUpdateExpiration = alwaysUpdateExpiration;
  }

  /**
   * @param expiration
   *          the expiration to set
   */
  public void updateExpiration() {
    if (alwaysUpdateExpiration) {
      this.expiration = System.currentTimeMillis() + DEFAULT_EXPIRATION;
    }
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (accountNonLocked ? 1231 : 1237);
    result = prime * result + ((authorities == null) ? 0 : authorities.hashCode());
    result = prime * result + (enabled ? 1231 : 1237);
    result = prime * result + ((username == null) ? 0 : username.hashCode());
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
    TokenUserDetails other = (TokenUserDetails) obj;
    if (accountNonLocked != other.accountNonLocked)
      return false;
    if (authorities == null) {
      if (other.authorities != null)
        return false;
    } else if (!authorities.equals(other.authorities))
      return false;
    if (enabled != other.enabled)
      return false;
    if (username == null) {
      if (other.username != null)
        return false;
    } else if (!username.equals(other.username))
      return false;
    return true;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "TokenUserDetails [username=" + username + ", authorities=" + authorities + ", accountNonLocked="
        + accountNonLocked + ", enabled=" + enabled + ", expiration=" + expiration + "]";
  }
}
