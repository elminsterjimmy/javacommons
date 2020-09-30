package com.elminster.spring.jpa.converter;

public class TestConverter extends EnumValueConverter<String, TestEnum> {

  public TestConverter() {
    super(TestEnum.class);
  }
}
