package com.elminster.common.config;

import com.elminster.common.config.key.Key;

import static com.elminster.common.config.key.Key.*;

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
  String getStringProperty(String key);

  /**
   * Get String property.
   * 
   * @param key
   *          the property key
   * @param defaultValue
   *          the default value if not found
   * @return the property.
   */
  String getStringProperty(String key, String defaultValue);

  /**
   * Get String property.
   * 
   * @param key
   *          the String key
   * @return the property.
   */
  String getStringProperty(StringKey key);

  /**
   * Get Integer property. Will throw a NFE if the property cannot cast to Integer.
   * 
   * @param key
   *          the property key
   * @return the property.
   */
  Integer getIntegerProperty(String key);

  /**
   * Get Integer property.
   * 
   * @param key
   *          the property key
   * @param defaultValue
   *          the default value if not found
   * @return the property.
   */
  Integer getIntegerProperty(String key, Integer defaultValue);

  /**
   * Get Integer property.
   * 
   * @param key
   *          the Integer key
   * @return the property.
   */
  Integer getIntegerProperty(IntegerKey key);

  /**
   * Get Long property. Will throw a NFE if the property cannot cast to Long.
   * 
   * @param key
   *          the property key
   * @return the property.
   */
  Long getLongProperty(String key);

  /**
   * Get Long property.
   * 
   * @param key
   *          the property key
   * @param defaultValue
   *          the default value if not found
   * @return the property.
   */
  Long getLongProperty(String key, Long defaultValue);

  /**
   * Get Integer property.
   * 
   * @param key
   *          the Long key
   * @return the property.
   */
  Long getLongProperty(LongKey key);

  /**
   * Get Float property. Will throw a NFE if the property cannot cast to Float.
   * 
   * @param key
   *          the property key
   * @return the property.
   */
  Float getFloatProperty(String key);

  /**
   * Get Float property.
   * 
   * @param key
   *          the property key
   * @param defaultValue
   *          the default value if not found
   * @return the property.
   */
  Float getFloatProperty(String key, Float defaultValue);

  /**
   * Get Float property.
   * 
   * @param key
   *          the Float key
   * @return the property.
   */
  Float getFloatProperty(FloatKey key);

  /**
   * Get Double property. Will throw a NFE if the property cannot cast to Double.
   * 
   * @param key
   *          the property key
   * @return the property.
   */
  Double getDoubleProperty(String key);

  /**
   * Get Double property.
   * 
   * @param key
   *          the property key
   * @param defaultValue
   *          the default value if not found
   * @return the property.
   */
  Double getDoubleProperty(String key, Double defaultValue);

  /**
   * Get Double property.
   * 
   * @param key
   *          the Double key
   * @return the property.
   */
  Double getDoubleProperty(DoubleKey key);

  /**
   * Get Boolean property.
   * 
   * @param key
   *          the property key
   * @return the property.
   */
  Boolean getBooleanProperty(String key);

  /**
   * Get Integer property.
   * 
   * @param key
   *          the property key
   * @param defaultValue
   *          the default value if not found
   * @return the property.
   */
  Boolean getBooleanProperty(String key, Boolean defaultValue);

  /**
   * Get Boolean property.
   * 
   * @param key
   *          the Boolean key
   * @return the property.
   */
  Boolean getBooleanProperty(BooleanKey key);

  /**
   * Set the String value.
   * 
   * @param key
   *          the property key
   * @param value
   *          the property value
   */
  void setProperty(String key, String value);

  /**
   * Set the Boolean value.
   * 
   * @param key
   *          the property key
   * @param boolValue
   *          the property value
   */
  void setProperty(String key, Boolean boolValue);

  /**
   * Set the Integer value.
   * 
   * @param key
   *          the property key
   * @param intValue
   *          the property value
   */
  void setProperty(String key, Integer intValue);

  /**
   * Set the Long value.
   * 
   * @param key
   *          the property key
   * @param longValue
   *          the property value
   */
  void setProperty(String key, Long longValue);

  /**
   * Set the String value.
   * 
   * @param key
   *          the property key
   * @param floatValue
   *          the property value
   */
  void setProperty(String key, Float floatValue);

  /**
   * Set the Double value.
   * 
   * @param key
   *          the property key
   * @param doubleValue
   *          the property value
   */
  void setProperty(String key, Double doubleValue);

  /**
   * Set the 
   * 
   * @param key
   *          the key
   */
  <E> void setProperties(Key<E> key);
}
