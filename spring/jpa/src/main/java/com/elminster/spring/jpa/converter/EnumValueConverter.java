package com.elminster.spring.jpa.converter;

import javax.persistence.AttributeConverter;

public class EnumValueConverter<T extends java.io.Serializable, E extends Enum<E> & DatabaseValue<T>>
    implements AttributeConverter<E, T> {

  private final Class<E> enumClass;

  public EnumValueConverter(Class<E> enumClass){
    this.enumClass = enumClass;
  }

  @Override
  public T convertToDatabaseColumn(E e) {
    if (null != e) {
      return e.getDatabaseValue();
    }
    return null;
  }

  @Override
  public E convertToEntityAttribute(T t) {
    for (E e : enumClass.getEnumConstants()) {
      if (t.equals(e.getDatabaseValue())) {
        return e;
      }
    }
    return null;
  }
}