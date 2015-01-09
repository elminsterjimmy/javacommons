package com.elminster.common.config;

import java.util.Properties;

/**
 * The Configuration.
 * 
 * @author jgu
 * @version 1.0
 */
abstract public class CommonConfiguration {

  /** the properties. */
  protected static final Properties properties = new Properties();

  /**
   * Constructor.
   */
  protected CommonConfiguration() {
    loadResources();
  }

  /**
   * Load the resource files.
   */
  protected void loadResources() {
  }

  /**
   * Get String property.
   * 
   * @param key
   *          the property key
   * @return the property.
   */
  public String getStringProperty(String key) {
    return properties.getProperty(key);
  }

  /**
   * Get String property.
   * 
   * @param key
   *          the property key
   * @param defaultValue
   *          the default value if not found
   * @return the property.
   */
  public String getStringProperty(String key, String defaultValue) {
    return properties.getProperty(key, defaultValue);
  }
  
  /**
   * Get Integer property.
   * 
   * @param key
   *          the property key
   * @return the property.
   */
  public Integer getIntegerProperty(String key) {
    String value = properties.getProperty(key);
    Integer rtn = null;
    if (null != value) {
      rtn = Integer.parseInt(value);
    }
    return rtn;
  }
  
  /**
   * Get Integer property.
   * 
   * @param key
   *          the property key
   * @param defaultValue
   *          the default value if not found
   * @return the property.
   */
  public Integer getIntegerProperty(String key, Integer defaultValue) {
    Integer rtn = null;
    try {
      rtn = getIntegerProperty(key);
    } catch (NumberFormatException nfe) {
      rtn = null;
    }
    if (null == rtn) {
      rtn = defaultValue;
    }
    return rtn;
  }
  
  /**
   * Get Boolean property.
   * 
   * @param key
   *          the property key
   * @return the property.
   */
  public Boolean getBooleanProperty(String key) {
    String value = properties.getProperty(key);
    Boolean rtn = null;
    if (null != value) {
      rtn = Boolean.valueOf(value);
    }
    return rtn;
  }
  
  /**
   * Get Integer property.
   * 
   * @param key
   *          the property key
   * @param defaultValue
   *          the default value if not found
   * @return the property.
   */
  public Boolean getBooleanProperty(String key, Boolean defaultValue) {
    Boolean rtn = null;
    try {
      rtn = getBooleanProperty(key);
    } catch (NumberFormatException nfe) {
      rtn = null;
    }
    if (null == rtn) {
      rtn = defaultValue;
    }
    return rtn;
  }
}
