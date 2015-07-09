package com.elminster.common.util;

public interface IValueConverter<T, K> {

  public T convert(K value) throws Exception;
}
