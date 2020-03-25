package com.elminster.common.config.key;

import static com.elminster.common.config.key.Key.*;

public class KeyBuilder {

  public static StringKey stringKey(String key, String defaultValue) {
    return new StringKey(key, defaultValue);
  }

  public static BooleanKey booleanKey(String key, Boolean defaultValue) {
    return new BooleanKey(key, defaultValue);
  }

  public static IntegerKey integerKey(String key, Integer defaultValue) {
    return new IntegerKey(key, defaultValue);
  }

  public static FloatKey floatKey(String key, Float defaultValue) {
    return new FloatKey(key, defaultValue);
  }

  public static LongKey longKey(String key, Long defaultValue) {
    return new LongKey(key, defaultValue);
  }

  public static DoubleKey doubleKey(String key, Double defaultValue) {
    return new DoubleKey(key, defaultValue);
  }
}
