package com.elminster.common.config.key;

/**
 * The Key used in Configuration.
 * 
 * @author jinggu
 * @version 1.0
 *
 * @param <T>
 */
public class Key<T> {

  /** the key name. */
  private final String key;
  /** the default value. */
  private final T defaultValue;
  
  public Key(final String key, final T defaultValue) {
    this.key = key;
    this.defaultValue = defaultValue;
  }
  
  /**
   * @return the key
   */
  public String getKey() {
    return key;
  }
  /**
   * @return the defaultValue
   */
  public T getDefaultValue() {
    return defaultValue;
  }


  /** String Key. */
  public static class StringKey extends Key<String> {
    public StringKey(String key, String defaultValue) {
      super(key, defaultValue);
    }
  }

  /** Integer Key. */
  public static class IntegerKey extends Key<Integer> {
    public IntegerKey(String key, Integer defaultValue) {
      super(key, defaultValue);
    }
  }

  /** Float Key. */
  public static class FloatKey extends Key<Float> {
    public FloatKey(String key, Float defaultValue) {
      super(key, defaultValue);
    }
  }

  /** Long Key. */
  public static class LongKey extends Key<Long> {
    public LongKey(String key, Long defaultValue) {
      super(key, defaultValue);
    }
  }

  /** Double Key. */
  public static class DoubleKey extends Key<Double> {
    public DoubleKey(String key, Double defaultValue) {
      super(key, defaultValue);
    }
  }

  /** Boolean Key. */
  public static class BooleanKey extends Key<Boolean> {
    public BooleanKey(String key, Boolean defaultValue) {
      super(key, defaultValue);
    }
  }
}
