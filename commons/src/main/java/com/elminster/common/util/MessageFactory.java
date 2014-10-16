package com.elminster.common.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Message factory for i18n.
 * 
 * @author Gu
 * @version 1.0
 * 
 */
public class MessageFactory {

  /***/
  private String packageName;
  /***/
  private Class<?> clazz;
  
  /** caches. */
  private static Map<Class<?>, MessageFactory> factories = new HashMap<Class<?>, MessageFactory>();
  
  /**
   * Constructor.
   * 
   * @param clazz the class
   */
  private MessageFactory(Class<?> clazz) {
    this.clazz = clazz;
    this.packageName = clazz.getPackage().getName();
  }
  
  /**
   * Get or create message factory via specified class.
   * 
   * @param clazz the class
   * @return the message factory
   */
  public static synchronized MessageFactory getInstance(Class<?> clazz) {
    MessageFactory mf = factories.get(clazz);
    if (null == mf) {
      mf = new MessageFactory(clazz);
      factories.put(clazz, mf);
    }
    return mf;
  }
  
  /**
   * Get localized message.
   * 
   * @param key message key
   * @param locale the locale
   * @param args the arguments
   * @return the localized message
   */
  public String getMessage(String key, Locale locale, Object... args) {
    try {
      return getLocalizedMessage(ResourceBundle.getBundle(packageName + ".resource",
          locale == null ? Locale.getDefault() : locale, clazz.getClassLoader()), key, args);
     } catch (MissingResourceException error) {
       try {
         return getLocalizedMessage(ResourceBundle.getBundle(packageName + ".resource",
             Locale.getDefault(), clazz.getClassLoader()), key, args);
       } catch (MissingResourceException e) {
         return key + " (missing resource)";
       }
     }
  }
  
  /**
   * Get localized message.
   * 
   * @param rb the resource bundle
   * @param key message key
   * @param args the arguments
   * @return the localized message
   */
  private String getLocalizedMessage(ResourceBundle rb, String key, Object... args) {
    return String.format(rb.getString(key), args);
  }
}
