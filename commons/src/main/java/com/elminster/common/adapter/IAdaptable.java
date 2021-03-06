package com.elminster.common.adapter;

/**
 * The adaptable interface that make the adapt between classes.
 * 
 * @author jgu
 * @version 1.0
 */
public interface IAdaptable {
  
  /**
   * Adapt the current object into the specified class.
   * @param clazz the class to adapt to
   * @return adapted object
   */
  public Object adaptTo(Class<?> clazz);
  
  /**
   * Check if current type is adaptable to the specified class.
   * @param clazz the class to adapt to
   * @return if current type is adaptable to the specified class
   */
  public boolean adaptable(Class<?> clazz);
}
