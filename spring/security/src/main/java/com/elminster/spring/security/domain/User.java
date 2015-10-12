package com.elminster.spring.security.domain;

import java.util.Date;
import java.util.List;

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
 * The default details. To extend this user details to fit your application.
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

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastLoginDate;

  @Column(length=15)
  private String lastLoginIp;

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(name = "auth_role_member",
    joinColumns = @JoinColumn(name = "userId"),
    inverseJoinColumns = @JoinColumn(name = "roleId"))
  private List<Role> roles;
  
  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(name = "auth_user_authority",
    joinColumns = @JoinColumn(name = "userId"),
    inverseJoinColumns = @JoinColumn(name = "authResLinkId"))
  private List<AuthorityResource> authorities;

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
  public void setUsername(String username) {
    this.username = username;
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
  public void setPassword(String password) {
    this.password = password;
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
  public void setEmail(String email) {
    this.email = email;
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
  public void setEnable(boolean enable) {
    this.enable = enable;
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
  public void setStatus(int status) {
    this.status = status;
  }

  /**
   * @return the createdDate
   */
  public Date getCreatedDate() {
    return createdDate;
  }

  /**
   * @param createdDate the createdDate to set
   */
  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
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
  public void setLastLoginDate(Date lastLoginDate) {
    this.lastLoginDate = lastLoginDate;
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
  public void setLastLoginIp(String lastLoginIp) {
    this.lastLoginIp = lastLoginIp;
  }

  /**
   * @return the roles
   */
  public List<Role> getRoles() {
    return roles;
  }

  /**
   * @param roles
   *          the roles to set
   */
  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }
  
  /**
   * @return the authorities
   */
  public List<AuthorityResource> getAuthorities() {
    return authorities;
  }

  /**
   * @param authorities the authorities to set
   */
  public void setAuthorities(List<AuthorityResource> authorities) {
    this.authorities = authorities;
  }
  
  @PrePersist
  public void setDefaultValue() {
    if (null == createdDate) {
      createdDate = new Date(System.currentTimeMillis());
    }
  }
}
