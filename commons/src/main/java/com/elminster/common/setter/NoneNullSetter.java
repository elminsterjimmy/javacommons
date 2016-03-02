package com.elminster.common.setter;

abstract public class NoneNullSetter implements ISetter {

  public void set(final Object value) {
    if (null != value) {
      doSet(value);
    }
  }
  
  abstract protected void doSet(Object value);
}
