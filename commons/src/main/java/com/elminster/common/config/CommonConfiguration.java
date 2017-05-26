package com.elminster.common.config;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.elminster.common.util.StringUtil;

/**
 * The Common Configuration.
 * 
 * @author jgu
 * @version 1.0
 */
abstract public class CommonConfiguration implements IConfigProvider, IConfigPersister {

  /** the logger. */
  protected static final Log logger = LogFactory.getLog(CommonConfiguration.class);

  /** the properties. */
  protected Properties properties = new Properties();

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
    String value = properties.getProperty(key);
    if (null == value) {
      logger.warn(getMissingKeyMessage(key));
    }
    return value;
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
   * Get String property.
   * 
   * @param key
   *          the String key
   * @return the property.
   */
  public String getStringProperty(StringKey key) {
    return properties.getProperty(key.getKey(), key.getDefaultValue());
  }

  /**
   * Get Integer property. Will throw a NFE if the property cannot cast to Integer.
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
    } else {
      logger.warn(getMissingKeyMessage(key));
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
   * Get Integer property.
   * 
   * @param key
   *          the integer key
   * @return the property.
   */
  public Integer getIntegerProperty(IntegerKey key) {
    return getIntegerProperty(key.getKey(), key.getDefaultValue());
  }

  /**
   * Get Long property. Will throw a NFE if the property cannot cast to Long.
   * 
   * @param key
   *          the property key
   * @return the property.
   */
  public Long getLongProperty(String key) {
    String value = properties.getProperty(key);
    Long rtn = null;
    if (null != value) {
      rtn = Long.parseLong(value);
    } else {
      logger.warn(getMissingKeyMessage(key));
    }
    return rtn;
  }

  /**
   * Get Long property.
   * 
   * @param key
   *          the property key
   * @param defaultValue
   *          the default value if not found
   * @return the property.
   */
  public Long getLongProperty(String key, Long defaultValue) {
    Long rtn = null;
    try {
      rtn = getLongProperty(key);
    } catch (NumberFormatException nfe) {
      rtn = null;
    }
    if (null == rtn) {
      rtn = defaultValue;
    }
    return rtn;
  }

  /**
   * Get Long property.
   * 
   * @param key
   *          the long key
   * @return the property.
   */
  public Long getLongProperty(LongKey key) {
    return getLongProperty(key.getKey(), key.getDefaultValue());
  }

  /**
   * Get Float property. Will throw a NFE if the property cannot cast to Float.
   * 
   * @param key
   *          the property key
   * @return the property.
   */
  public Float getFloatProperty(String key) {
    String value = properties.getProperty(key);
    Float rtn = null;
    if (null != value) {
      rtn = Float.parseFloat(value);
    } else {
      logger.warn(getMissingKeyMessage(key));
    }
    return rtn;
  }

  /**
   * Get Float property.
   * 
   * @param key
   *          the property key
   * @param defaultValue
   *          the default value if not found
   * @return the property.
   */
  public Float getFloatProperty(String key, Float defaultValue) {
    Float rtn = null;
    try {
      rtn = getFloatProperty(key);
    } catch (NumberFormatException nfe) {
      rtn = null;
    }
    if (null == rtn) {
      rtn = defaultValue;
    }
    return rtn;
  }

  /**
   * Get Float property.
   * 
   * @param key
   *          the float key
   * @return the property.
   */
  public Float getFloatProperty(FloatKey key) {
    return getFloatProperty(key.getKey(), key.getDefaultValue());
  }

  /**
   * Get Double property. Will throw a NFE if the property cannot cast to Double.
   * 
   * @param key
   *          the property key
   * @return the property.
   */
  public Double getDoubleProperty(String key) {
    String value = properties.getProperty(key);
    Double rtn = null;
    if (null != value) {
      rtn = Double.parseDouble(value);
    } else {
      logger.warn(getMissingKeyMessage(key));
    }
    return rtn;
  }

  /**
   * Get Double property.
   * 
   * @param key
   *          the property key
   * @param defaultValue
   *          the default value if not found
   * @return the property.
   */
  public Double getDoubleProperty(String key, Double defaultValue) {
    Double rtn = null;
    try {
      rtn = getDoubleProperty(key);
    } catch (NumberFormatException nfe) {
      rtn = null;
    }
    if (null == rtn) {
      rtn = defaultValue;
    }
    return rtn;
  }

  /**
   * Get Double property.
   * 
   * @param key
   *          the double key
   * @return the property.
   */
  public Double getDoubleProperty(DoubleKey key) {
    return getDoubleProperty(key.getKey(), key.getDefaultValue());
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
      rtn = StringUtil.string2Boolean(value);
    } else {
      logger.warn(getMissingKeyMessage(key));
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

  /**
   * Get Boolean property.
   * 
   * @param key
   *          the boolean key
   * @return the property.
   */
  public Boolean getBooleanProperty(BooleanKey key) {
    return getBooleanProperty(key.getKey(), key.getDefaultValue());
  }

  /**
   * @see com.elminster.common.config.IConfigProvider#setProperty(java.lang.String, java.lang.String)
   */
  @Override
  public void setProperty(String key, String value) {
    properties.put(key, value);
  }

  /**
   * @see com.elminster.common.config.IConfigProvider#setProperty(java.lang.String, java.lang.Boolean)
   */
  @Override
  public void setProperty(String key, Boolean boolValue) {
    properties.put(key, String.valueOf(boolValue));
  }

  /**
   * @see com.elminster.common.config.IConfigProvider#setProperty(java.lang.String, java.lang.Integer)
   */
  @Override
  public void setProperty(String key, Integer intValue) {
    properties.put(key, String.valueOf(intValue));
  }

  /**
   * @see com.elminster.common.config.IConfigProvider#setProperty(java.lang.String, java.lang.Long)
   */
  @Override
  public void setProperty(String key, Long longValue) {
    properties.put(key, String.valueOf(longValue));
  }

  /**
   * @see com.elminster.common.config.IConfigProvider#setProperty(java.lang.String, java.lang.Float)
   */
  @Override
  public void setProperty(String key, Float floatValue) {
    properties.put(key, String.valueOf(floatValue));
  }

  /**
   * @see com.elminster.common.config.IConfigProvider#setProperty(java.lang.String, java.lang.Double)
   */
  @Override
  public void setProperty(String key, Double doubleValue) {
    properties.put(key, String.valueOf(doubleValue));
  }

  /**
   * @see com.elminster.common.config.IConfigPersister#persist()
   */
  @Override
  public void persist() throws IOException {
  }

  /**
   * Get the missing key message.
   * 
   * @param key
   *          the key
   * @return the missing key message
   */
  private String getMissingKeyMessage(String key) {
    return "Property is missing for key: [" + key + "].";
  }
}
