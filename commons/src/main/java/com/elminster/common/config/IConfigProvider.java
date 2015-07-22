package com.elminster.common.config;

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
   * Set the String value.
   * @param key the property key
   * @param value the property value
   */
  public void setProperty(String key, String value);
  
  /**
   * Set the Boolean value.
   * @param key the property key
   * @param value the property value
   */
  public void setProperty(String key, Boolean boolValue);
  
  /**
   * Set the Integer value.
   * @param key the property key
   * @param value the property value
   */
  public void setProperty(String key, Integer intValue);
  
  /**
   * Set the Long value.
   * @param key the property key
   * @param value the property value
   */
  public void setProperty(String key, Long longValue);
  
  /**
   * Set the String value.
   * @param key the property key
   * @param value the property value
   */
  public void setProperty(String key, Float floatValue);
  
  /**
   * Set the Double value.
   * @param key the property key
   * @param value the property value
   */
  public void setProperty(String key, Double doubleValue);
}
