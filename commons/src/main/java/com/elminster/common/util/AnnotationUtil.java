package com.elminster.common.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * The annotation utilities.
 * 
 * @author jgu
 * @version 1.0
 */
public abstract class AnnotationUtil {

  /**
   * Filter the fields with annotation class.
   * @param fields the fields
   * @param annotationClass the annotation class
   * @return filtered fileds
   */
  public static Field[] filterFieldWithAnnotation(final Field[] fields,
      final Class<? extends Annotation> annotationClass) {
    if (null == fields) {
      return null;
    }
    Field[] filteredField = new Field[fields.length];
    int i = 0;
    for (Field field : fields) {
      Annotation anno = field.getAnnotation(annotationClass);
      if (null != anno) {
        filteredField[i++] = field;
      }
    }
    filteredField = (Field[]) ArrayUtil.resize(filteredField, i);
    return filteredField;
  }

  /**
   * Filter the methods with annotation class.
   * @param methods the methods
   * @param annotationClass the annotation class
   * @return the filtered methods
   */
  public static Method[] filterMethodWithAnnotation(final Method[] methods,
      final Class<? extends Annotation> annotationClass) {
    if (null == methods) {
      return null;
    }
    Method[] filteredMethod = new Method[methods.length];
    int i = 0;
    for (Method method : methods) {
      Annotation anno = method.getAnnotation(annotationClass);
      if (null != anno) {
        filteredMethod[i++] = method;
      }
    }
    filteredMethod = (Method[]) ArrayUtil.resize(filteredMethod, i);
    return filteredMethod;
  }
}
