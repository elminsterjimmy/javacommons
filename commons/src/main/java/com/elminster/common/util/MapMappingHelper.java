package com.elminster.common.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

import com.elminster.common.annotation.MappingProperty;

/**
 * The helper that mapping between the specified object and map via reflection.
 * 
 * If the specified is annotated with MappingProperty, than it will take the name as map key,
 * otherwise the filed name will be token.
 * 
 * @author jgu
 * @version 1.0
 */
public class MapMappingHelper {
  
  /**
   * Mapping the map to the target object.
   * @param map the map
   * @param target the target
   */
  public static void mappingMapToObject(Map<String, Object> map, Object target) {
    if (CollectionUtil.isNotEmpty(map)) {
      if (null != target) {
        Class<?> clazz = target.getClass();
        Field[] fields = ReflectUtil.getAllField(clazz);
        Set<String> keySet = map.keySet();
        Field f = null;
        for (String key : keySet) {
          f = getField(key, fields);
          if (null != f) {
            Object value = map.get(key);
            setValue(target, value, f);
          }
        }
      }
    }
  }
  
  /**
   * Set the value of the target's field.
   * @param target the target object
   * @param value the value
   * @param field the field
   */
  @SuppressWarnings("unchecked")
  private static void setValue(Object target, Object value, Field field) {
    Class<?> type = field.getType();
    // primitive class
    type = type.isPrimitive() ? ReflectUtil.getWrappedClass(type) : type;
    try {
      if (value.getClass().isAssignableFrom(type)) {
          ReflectUtil.setField(target, field, value);
      } else if (canCast(value.getClass(), type)) {
        ReflectUtil.setField(target, field, castValue(value, type));
      } else if (value instanceof Map) {
        Object instance = type.newInstance();
        mappingMapToObject((Map<String, Object>) value, instance);
        ReflectUtil.setField(target, field, instance);
      }
    } catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
      e.printStackTrace();
    }
  }

  /**
   * String <=> Number
   * String <=> Boolean
   * Number <=> Boolean
   * 
   * @param from the class cast from
   * @param to the class cast to
   * @return can the class cast?
   */
  private static boolean canCast(Class<?> from, Class<?> to) {
    // primitive classes
    from = from.isPrimitive() ? ReflectUtil.getWrappedClass(from) : from;
    to = to.isPrimitive() ? ReflectUtil.getWrappedClass(to) : to;
    
    if (String.class.equals(from)) {
      if (Number.class.isAssignableFrom(to) || Boolean.class.equals(to)) {
        return true;
      }
    }
    if (Boolean.class.equals(from)) {
      if (String.class.equals(to)) {
        return true;
      }
    }
    if (Number.class.isAssignableFrom(from)) {
      if (String.class.equals(to)) {
        return true;
      }
    }
    if (Number.class.isAssignableFrom(from)) {
      if (Number.class.isAssignableFrom(to)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Cast the value.
   * String <=> Number
   * String <=> Boolean
   * Number <=> Boolean
   * 
   * @param value the value
   * @param to cast to class
   * @return casted value
   */
  private static Object castValue(Object value, Class<?> to) {
    Class<?> from = value.getClass();
    // primitive classes
    from = from.isPrimitive() ? ReflectUtil.getWrappedClass(from) : from;
    to = to.isPrimitive() ? ReflectUtil.getWrappedClass(to) : to;
    try {
      if (String.class.equals(from)) {
        if (Number.class.isAssignableFrom(to) || Boolean.class.equals(to)) {
          Constructor<?> constructor = to.getConstructor(String.class);
          if (null != constructor) {
              return constructor.newInstance(value);
          }
        }
      }
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException | NoSuchMethodException | SecurityException e) {
      // ignore
    }
    if (Number.class.isAssignableFrom(from) || Boolean.class.equals(from)) {
      if (String.class.equals(to)) {
        return String.valueOf(value);
      }
    }
    if (Number.class.isAssignableFrom(from)) {
      if (Number.class.isAssignableFrom(to)) {
        if (to.equals(Integer.class)) {
          return ((Number)value).intValue();
        } else if (to.equals(Byte.class)) {
          return ((Number)value).byteValue();
        } else if (to.equals(Double.class)) {
          return ((Number)value).doubleValue();
        } else if (to.equals(Float.class)) {
          return ((Number)value).floatValue();
        } else if (to.equals(Long.class)) {
          return ((Number)value).longValue();
        } else if (to.equals(Short.class)) {
          return ((Number)value).shortValue();
        }
      }
    }
    return null;
  }

  /**
   * Get the field with key.
   * @param key the key
   * @param fields the fields
   * @return the field with the key
   */
  private static Field getField(String key, Field[] fields) {
    for (Field field : fields) {
      String name = field.getName();
      MappingProperty anno = field.getAnnotation(MappingProperty.class);
      if (null != anno) {
        String annoName = anno.getName();
        if (StringUtil.isNotEmpty(annoName)) {
          name = annoName;
        }
      }
      if (name.equals(key)) {
        return field;
      }
    }
    return null;
  }

  /**
   * Mapping the target obejct to the map.
   * @param target the target
   * @param map the map
   */
  public static void mappingObjectToMap(Object target, Map<String, Object> map) {
    if (null != target) {
      Class<?> clazz = target.getClass();
      Field[] fields = ReflectUtil.getAllField(clazz);
      for (Field field : fields) {
        String name = field.getName();
        MappingProperty anno = field.getAnnotation(MappingProperty.class);
        if (null != anno) {
          String annoName = anno.getName();
          if (StringUtil.isNotEmpty(annoName)) {
            name = annoName;
          }
        }
        try {
          map.put(name, ReflectUtil.getFieldValue(target, field));
        } catch (IllegalArgumentException | IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
