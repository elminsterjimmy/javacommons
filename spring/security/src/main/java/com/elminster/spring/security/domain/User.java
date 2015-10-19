package com.elminster.spring.security.domain;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Email;

/**
 * The default user. To extend this user to fit your application.
 * 
 * @author jgu
 * @version 1.0
 */
@Entity
@Table(name = "auth_users")
public class User {

  // @formatter:off
  @Id
  @GeneratedValue(generator="id_gen")
  @GenericGenerator(name="id_gen", strategy="enhanced-table", 
    parameters = {
      @Parameter(name="table_name", value="sequence_id_gen"), 
      @Parameter(name="value_column_name", value="next"), 
      @Parameter(name="segment_column_name",value="segment_name"), 
      @Parameter(name="segment_value", value="user_seq"),
      @Parameter(name="increment_size", value="10"), 
      @Parameter(name="optimizer", value="pooled-lo") 
    })
  // @formatter:on
  private int id;

  @Column(unique=true, nullable=false, length=64)
  private String username;

  @Column(unique=true, nullable=false, length=64)
  private String password;

  @Column(nullable=false, length=255)
  @Email
  private String email;

  @Column(nullable=false, length=1)
  private boolean enable = true;

  @Column(nullable=false)
  private int status = 0;

  @Column(updatable=false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDate;

  private Date lastLoginDate;

  private String lastLoginIp;

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(name = "auth_role_member",
    joinColumns = @JoinColumn(name = "userId"),
    inverseJoinColumns = @JoinColumn(name = "roleId"))
  private Set<Role> roles;
  
  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(name = "auth_user_authority",
    joinColumns = @JoinColumn(name = "userId"),
    inverseJoinColumns = @JoinColumn(name = "authResLinkId"))
  private Set<AuthorityResource> authorities;

  /**
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * @param username
   *          the username to set
   */
  public User setUsername(String username) {
    this.username = username;
    return this;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password
   *          the password to set
   */
  public User setPassword(String password) {
    this.password = password;
    return this;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email
   *          the email to set
   */
  public User setEmail(String email) {
    this.email = email;
    return this;
  }

  /**
   * @return the enable
   */
  public boolean isEnable() {
    return enable;
  }

  /**
   * @param enable
   *          the enable to set
   */
  public User setEnable(boolean enable) {
    this.enable = enable;
    return this;
  }

  /**
   * @return the status
   */
  public int getStatus() {
    return status;
  }

  /**
   * @param status
   *          the status to set
   */
  public User setStatus(int status) {
    this.status = status;
    return this;
  }

  /**
   * @return the createdDate
   */
  public Date getCreatedDate() {
    return createdDate;
  }

  /**
   * @return the lastLoginDate
   */
  public Date getLastLoginDate() {
    return lastLoginDate;
  }

  /**
   * @param lastLoginDate the lastLoginDate to set
   */
  public User setLastLoginDate(Date lastLoginDate) {
    this.lastLoginDate = lastLoginDate;
    return this;
  }

  /**
   * @return the lastLoginIp
   */
  public String getLastLoginIp() {
    return lastLoginIp;
  }

  /**
   * @param lastLoginIp
   *          the lastLoginIp to set
   */
  public User setLastLoginIp(String lastLoginIp) {
    this.lastLoginIp = lastLoginIp;
    return this;
  }

  /**
   * @return the roles
   */
  public Set<Role> getRoles() {
    return roles;
  }

  /**
   * @param roles
   *          the roles to set
   */
  public User setRoles(Set<Role> roles) {
    this.roles = roles;
    return this;
  }
  
  public User addRole(Role role) {
    if (null != role) {
      if (null == this.roles) {
        this.roles = new TreeSet<Role>();
      }
      roles.add(role);
    }
    return this;
  }
  
  public User removeRole(Role role) {
    if (null != this.roles) {
      this.roles.remove(role);
    }
    return this;
  }
  
  /**
   * @return the authorities
   */
  public Set<AuthorityResource> getAuthorities() {
    return authorities;
  }

  /**
   * @param authorities the authorities to set
   */
  public User setAuthorities(Set<AuthorityResource> authorities) {
    this.authorities = authorities;
    return this;
  }
  
  public User addAuthorities(AuthorityResource authority) {
    if (null != authority) {
      if (null == this.authorities) {
        this.authorities = new TreeSet<AuthorityResource>();
      }
      authorities.add(authority);
    }
    return this;
  }
  
  public User removeAuthorities(AuthorityResource authority) {
    if (null != this.authorities) {
      authorities.remove(authority);
    }
    return this;
  }
  
  @PrePersist
  public void setDefaultValue() {
    if (null == createdDate) {
      createdDate = new Date(System.currentTimeMillis());
    }
  }
}
