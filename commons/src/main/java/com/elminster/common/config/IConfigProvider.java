package com.elminster.common.config;

import com.elminster.common.config.key.Key;

/**
 * The configuration provider that provides a variety of get/set methods.
 * 
 * @author jgu
 * @version 1.0
 */
public interface IConfigProvider {

  /**
   * Get String property.
   * 
   * @param key
   *          the property key
   * @return the property.
   */
  public String getStringProperty(String key);

  /**
   * Get String property.
   * 
   * @param key
   *          the property key
   * @param defaultValue
   *          the default value if not found
   * @return the property.
   */
  public String getStringProperty(String key, String defaultValue);

  /**
   * Get String property.
   * 
   * @param key
   *          the String key
   * @return the property.
   */
  public String getStringProperty(StringKey key);

  /**
   * Get Integer property. Will throw a NFE if the property cannot cast to Integer.
   * 
   * @param key
   *          the property key
   * @return the property.
   */
  public Integer getIntegerProperty(String key);

  /**
   * Get Integer property.
   * 
   * @param key
   *          the property key
   * @param defaultValue
   *          the default value if not found
   * @return the property.
   */
  public Integer getIntegerProperty(String key, Integer defaultValue);

  /**
   * Get Integer property.
   * 
   * @param key
   *          the Integer key
   * @return the property.
   */
  public Integer getIntegerProperty(IntegerKey key);

  /**
   * Get Long property. Will throw a NFE if the property cannot cast to Long.
   * 
   * @param key
   *          the property key
   * @return the property.
   */
  public Long getLongProperty(String key);

  /**
   * Get Long property.
   * 
   * @param key
   *          the property key
   * @param defaultValue
   *          the default value if not found
   * @return the property.
   */
  public Long getLongProperty(String key, Long defaultValue);

  /**
   * Get Integer property.
   * 
   * @param key
   *          the Long key
   * @return the property.
   */
  public Long getLongProperty(LongKey key);

  /**
   * Get Float property. Will throw a NFE if the property cannot cast to Float.
   * 
   * @param key
   *          the property key
   * @return the property.
   */
  public Float getFloatProperty(String key);

  /**
   * Get Float property.
   * 
   * @param key
   *          the property key
   * @param defaultValue
   *          the default value if not found
   * @return the property.
   */
  public Float getFloatProperty(String key, Float defaultValue);

  /**
   * Get Float property.
   * 
   * @param key
   *          the Float key
   * @return the property.
   */
  public Float getFloatProperty(FloatKey key);

  /**
   * Get Double property. Will throw a NFE if the property cannot cast to Double.
   * 
   * @param key
   *          the property key
   * @return the property.
   */
  public Double getDoubleProperty(String key);

  /**
   * Get Double property.
   * 
   * @param key
   *          the property key
   * @param defaultValue
   *          the default value if not found
   * @return the property.
   */
  public Double getDoubleProperty(String key, Double defaultValue);

  /**
   * Get Double property.
   * 
   * @param key
   *          the Double key
   * @return the property.
   */
  public Double getDoubleProperty(DoubleKey key);

  /**
   * Get Boolean property.
   * 
   * @param key
   *          the property key
   * @return the property.
   */
  public Boolean getBooleanProperty(String key);

  /**
   * Get Integer property.
   * 
   * @param key
   *          the property key
   * @param defaultValue
   *          the default value if not found
   * @return the property.
   */
  public Boolean getBooleanProperty(String key, Boolean defaultValue);

  /**
   * Get Boolean property.
   * 
   * @param key
   *          the Boolean key
   * @return the property.
   */
  public Boolean getBooleanProperty(BooleanKey key);

  /**
   * Set the String value.
   * 
   * @param key
   *          the property key
   * @param value
   *          the property value
   */
  public void setProperty(String key, String value);

  /**
   * Set the Boolean value.
   * 
   * @param key
   *          the property key
   * @param value
   *          the property value
   */
  public void setProperty(String key, Boolean boolValue);

  /**
   * Set the Integer value.
   * 
   * @param key
   *          the property key
   * @param value
   *          the property value
   */
  public void setProperty(String key, Integer intValue);

  /**
   * Set the Long value.
   * 
   * @param key
   *          the property key
   * @param value
   *          the property value
   */
  public void setProperty(String key, Long longValue);

  /**
   * Set the String value.
   * 
   * @param key
   *          the property key
   * @param value
   *          the property value
   */
  public void setProperty(String key, Float floatValue);

  /**
   * Set the Double value.
   * 
   * @param key
   *          the property key
   * @param value
   *          the property value
   */
  public void setProperty(String key, Double doubleValue);

  /**
   * Set the key.
   * 
   * @param E
   *          the key type
   * @param key
   *          the key
   */
  public <E> void setProperties(Key<E> key);

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
