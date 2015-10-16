package com.elminster.spring.security.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="auth_roles")
public class Role {
  
  //@formatter:off
  @Id
  @GeneratedValue(generator="id_gen")
  @GenericGenerator(name="id_gen", strategy="enhanced-table", 
    parameters = {
      @Parameter(name="table_name", value="sequence_id_gen"), 
      @Parameter(name="value_column_name", value="next"), 
      @Parameter(name="segment_column_name",value="segment_name"), 
      @Parameter(name="segment_value", value="role_seq"),
      @Parameter(name="increment_size", value="10"), 
      @Parameter(name="optimizer", value="pooled-lo") 
    })
  // @formatter:on
  private int id;
  
  @Column(unique=true, nullable=false, length=255)
  private String name;
  
  @Column(nullable=true, length=1024)
  private String description;
  
  @Column(nullable=false, length=1)
  private boolean enable = true;
  
  @ManyToMany(mappedBy="roles")
  private Set<User> users;
  
  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(name = "auth_role_authority",
    joinColumns = @JoinColumn(name = "roleId"),
    inverseJoinColumns = @JoinColumn(name = "authResLinkId"))
  private Set<AuthorityResource> authorities;
  
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
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return the enable
   */
  public boolean isEnable() {
    return enable;
  }

  /**
   * @param enable the enable to set
   */
  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  /**
   * @return the users
   */
  public Set<User> getUsers() {
    return users;
  }

  /**
   * @param users the users to set
   */
  public void setUsers(Set<User> users) {
    this.users = users;
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
  public void setAuthorities(Set<AuthorityResource> authorities) {
    this.authorities = authorities;
  }
}
