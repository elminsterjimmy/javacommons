package com.elminster.spring.security.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="auth_resources")
public class Resource {

  //@formatter:off
  @Id
  @GeneratedValue(generator="id_gen")
  @GenericGenerator(name="id_gen", strategy="enhanced-table", 
    parameters = {
      @Parameter(name="table_name", value="sequence_id_gen"), 
      @Parameter(name="value_column_name", value="next"), 
      @Parameter(name="segment_column_name",value="segment_name"), 
      @Parameter(name="segment_value", value="resource_seq"),
      @Parameter(name="increment_size", value="10"), 
      @Parameter(name="optimizer", value="pooled-lo")
    })
  // @formatter:on
  private int id;
  
  @Column(nullable=false, length=255)
  private String name;
  
  @Column(nullable=false, length=5)
  private Integer type;
  
  @Column(nullable=true, length=1024)
  private String path;
  
  @Column(nullable=true, length=1024)
  private String description;
  
  @Column(nullable=false, length=1)
  private boolean enable = true;
  
  @ManyToOne
  @JoinColumn(name="parentId")
  private Resource parent;
  
  @OneToMany(mappedBy="parent")
  private Set<Resource> children;
  
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
   * @return the type
   */
  public Integer getType() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(Integer type) {
    this.type = type;
  }

  /**
   * @return the path
   */
  public String getPath() {
    return path;
  }

  /**
   * @param path the path to set
   */
  public void setPath(String path) {
    this.path = path;
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
   * @return the parent
   */
  public Resource getParent() {
    return parent;
  }

  /**
   * @param parent the parent to set
   */
  public void setParent(Resource parent) {
    this.parent = parent;
  }

  /**
   * @return the children
   */
  public Set<Resource> getChildren() {
    return children;
  }

  /**
   * @param children the children to set
   */
  public void setChildren(Set<Resource> children) {
    this.children = children;
  }
}
