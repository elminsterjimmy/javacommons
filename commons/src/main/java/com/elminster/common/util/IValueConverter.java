package com.elminster.common.util;

public interface IValueConverter<T, K> {

  T convert(K value) throws Exception;
}
