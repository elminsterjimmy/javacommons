package com.elminster.spring.jpa.converter;

public enum TestEnum implements DatabaseValue<String> {

  ZERO("0"),
  ONE("1");

  private String value;

  TestEnum(String value) {
    this.value = value;
  }

  @Override
  public String getDatabaseValue() {
    return value;
  }
}
