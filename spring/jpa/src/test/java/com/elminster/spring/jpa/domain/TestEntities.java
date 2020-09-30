package com.elminster.spring.jpa.domain;

import com.elminster.spring.jpa.converter.TestConverter;
import com.elminster.spring.jpa.converter.TestEnum;

import javax.persistence.*;

@Entity
@Table(name = "test")
public class TestEntities {

  private String id;

  private TestEnum value;

  @Id
  @Column(name = "id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Basic
  @Convert(converter = TestConverter.class)
  public TestEnum getValue() {
    return value;
  }

  public void setValue(TestEnum value) {
    this.value = value;
  }
}
