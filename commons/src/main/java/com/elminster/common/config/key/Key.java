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
}
