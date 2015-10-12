package com.elminster.spring.security.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="auth_authority_resource")
public class AuthorityResource {

  //@formatter:off
  @Id
  @GeneratedValue(generator="id_gen")
  @GenericGenerator(name="id_gen", strategy="enhanced-table", 
    parameters = {
      @Parameter(name="table_name", value="sequence_id_gen"), 
      @Parameter(name="value_column_name", value="next"), 
      @Parameter(name="segment_column_name",value="segment_name"), 
      @Parameter(name="segment_value", value="authority_resource_seq"),
      @Parameter(name="increment_size", value="10"), 
      @Parameter(name="optimizer", value="pooled-lo")
    })
  // @formatter:on
  private int id;
  
  @Column(unique=true, nullable=false, length=255)
  private String name;
  
  @OneToOne
  @JoinColumn(name="authorityId", foreignKey=@ForeignKey(name="fk_authority_auth_res_link"))
  private Authority authority;
  
  @OneToOne
  @JoinColumn(name="resourceId", foreignKey=@ForeignKey(name="fk_resource_auth_res_link"))
  private Resource resource;
  
  @Column(nullable=false, length=1)
  private boolean enable = true;

  
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
   * @return the authority
   */
  public Authority getAuthority() {
    return authority;
  }

  /**
   * @param authority the authority to set
   */
  public void setAuthority(Authority authority) {
    this.authority = authority;
  }

  /**
   * @return the resource
   */
  public Resource getResource() {
    return resource;
  }

  /**
   * @param resource the resource to set
   */
  public void setResource(Resource resource) {
    this.resource = resource;
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
}
