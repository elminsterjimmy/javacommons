package com.elminster.spring.security.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="auth_cookie_user")
public class CookieUser {
  
  //@formatter:off
  @Id
  @GeneratedValue(generator="id_gen")
  @GenericGenerator(name="id_gen", strategy="enhanced-table", 
    parameters = {
      @Parameter(name="table_name", value="auth_sequence_id_gen"), 
      @Parameter(name="value_column_name", value="next"), 
      @Parameter(name="segment_column_name",value="segment_name"), 
      @Parameter(name="segment_value", value="cookie_user_seq"),
      @Parameter(name="increment_size", value="10"), 
      @Parameter(name="optimizer", value="pooled-lo")
    })
  // @formatter:on
  private int id;
  
  @Column(unique=true, nullable=false, length=128)
  private String cookie;
  
  @Column
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastLogin;
  
  @Column(length=15)
  private String ipaddr;
  
  @OneToOne
  @JoinColumn(name="userId", foreignKey=@ForeignKey(name="fk_cookie_user"))
  private User user;

  /**
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * @return the cookie
   */
  public String getCookie() {
    return cookie;
  }

  /**
   * @param cookie the cookie to set
   */
  public void setCookie(String cookie) {
    this.cookie = cookie;
  }

  /**
   * @return the lastLogin
   */
  public Date getLastLogin() {
    return lastLogin;
  }

  /**
   * @param lastLogin the lastLogin to set
   */
  public void setLastLogin(Date lastLogin) {
    this.lastLogin = lastLogin;
  }

  /**
   * @return the ipaddr
   */
  public String getIpaddr() {
    return ipaddr;
  }

  /**
   * @param ipaddr the ipaddr to set
   */
  public void setIpaddr(String ipaddr) {
    this.ipaddr = ipaddr;
  }

  /**
   * @return the user
   */
  public User getUser() {
    return user;
  }

  /**
   * @param user the user to set
   */
  public void setUser(User user) {
    this.user = user;
  }
}
