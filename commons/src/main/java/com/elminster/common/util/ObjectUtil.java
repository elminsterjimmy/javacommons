package com.elminster.common.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import com.elminster.common.constants.Constants.StringConstants;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Object Utilities
 *
 * @author Gu
 * @version 1.0
 */
public abstract class ObjectUtil {
  /** Start of an Array: <code>[</code> */
  private static final String ARRAY_START = StringConstants.LEFT_SQUARE_BRACKETS;
  /** End of an Array: <code>]</code> */
  private static final String ARRAY_END = StringConstants.RIGHT_SQUARE_BRACKETS;

  /**
   * Determine if the specified Object is equal
   *
   * @param o1
   *     first Object to compare
   * @param o2
   *     second Object to compare
   * @return whether the Objects equal
   */
  public static boolean isEqual(Object o1, Object o2) {
    if (null == o1 && null == o2) {
      return true;
    }
    if (o1 == o2) {
      return true;
    }
    if (null == o1 || null == o2) {
      return false;
    }
    if (o1.equals(o2)) {
      return true;
    }
    if (o1.getClass().isArray() && o2.getClass().isArray()) {
      if (o1 instanceof Object[] && o2 instanceof Object[]) {
        return Arrays.equals((Object[]) o1, (Object[]) o2);
      }
      if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
        return Arrays.equals((boolean[]) o1, (boolean[]) o2);
      }
      if (o1 instanceof byte[] && o2 instanceof byte[]) {
        return Arrays.equals((byte[]) o1, (byte[]) o2);
      }
      if (o1 instanceof char[] && o2 instanceof char[]) {
        return Arrays.equals((char[]) o1, (char[]) o2);
      }
      if (o1 instanceof double[] && o2 instanceof double[]) {
        return Arrays.equals((double[]) o1, (double[]) o2);
      }
      if (o1 instanceof float[] && o2 instanceof float[]) {
        return Arrays.equals((float[]) o1, (float[]) o2);
      }
      if (o1 instanceof int[] && o2 instanceof int[]) {
        return Arrays.equals((int[]) o1, (int[]) o2);
      }
      if (o1 instanceof long[] && o2 instanceof long[]) {
        return Arrays.equals((long[]) o1, (long[]) o2);
      }
      if (o1 instanceof short[] && o2 instanceof short[]) {
        return Arrays.equals((short[]) o1, (short[]) o2);
      }
    }
    return false;
  }

  /**
   * Return a String representation of the specified Object
   *
   * @param obj
   *     the object to build a String representation for
   * @return a String representation of the Object
   */
  public static String toString(Object obj) {
    if (null == obj) {
      return StringConstants.EMPTY_STRING;
    }
    if (obj.getClass().isArray()) {
      if (obj instanceof short[]) {
        return toString((short[]) obj);
      } else if (obj instanceof long[]) {
        return ArrayUtil.toString((long[]) obj);
      } else if (obj instanceof int[]) {
        return ArrayUtil.toString((int[]) obj);
      } else if (obj instanceof float[]) {
        return ArrayUtil.toString((float[]) obj);
      } else if (obj instanceof double[]) {
        return ArrayUtil.toString((double[]) obj);
      } else if (obj instanceof char[]) {
        return ArrayUtil.toString((char[]) obj);
      } else if (obj instanceof boolean[]) {
        return ArrayUtil.toString((boolean[]) obj);
      } else if (obj instanceof byte[]) {
        return ArrayUtil.toString((byte[]) obj);
      } else if (obj instanceof Object[]) {
        return ArrayUtil.toString((Object[]) obj);
      } else {
        return StringConstants.EMPTY_STRING;
      }
    } else {
      return obj.toString();
    }
  }

  /**
   * Return a String which contains all fields key-value pair.
   *
   * @param obj
   *     specified Object
   * @return a String which contains all fields key-value pair
   */
  public static String buildToStringByReflect(Object obj) {
    return ToStringBuilder.reflectionToString(obj);
  }

  /**
   * Convert the given array (which may be a primitive array) to an object array.
   *
   * @param array
   *     the array to convert
   * @return the corresponding object array
   */
  public static Object[] toObjectArray(Object array) {
    if (null == array) {
      return new Object[0];
    }
    if (array instanceof Object[]) {
      return (Object[]) array;
    }
    Object[] newArray;
    if (!array.getClass().isArray()) {
      //
      Class<?> wrapperType = array.getClass();
      newArray = (Object[]) Array.newInstance(wrapperType, 1);
      newArray[0] = array;
    } else {
      int length = Array.getLength(array);
      if (0 == length) {
        return new Object[0];
      }
      Class<?> wrapperType = Array.get(array, 0).getClass();
      newArray = (Object[]) Array.newInstance(wrapperType, length);
      for (int i = 0; i < length; i++) {
        newArray[i] = Array.get(array, i);
      }
    }
    return newArray;
  }

  public static Object copyProperties(Object src, Object dest) throws Exception {
    return copyProperties(src, dest, null, null);
  }

  public static Object copyPropertiesSkipCollection(Object src, Object dest) throws Exception {
    return copyProperties(src, dest, null, null, false);
  }

  public static Object copyProperties(Object src, Object dest, Map<String, String> fieldNameMapping) throws Exception {
    return copyProperties(src, dest, fieldNameMapping, null);
  }

  public static <T, K> Object copyProperties(Object src, Object dest, Map<String, String> fieldNameMapping,
                                             Map<String, IValueConverter<T, K>> fieldValueConverter) throws Exception {
    return copyProperties(src, dest, fieldNameMapping, fieldValueConverter, true);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public static <T, K> Object copyProperties(Object src, Object dest, Map<String, String> fieldNameMapping,
                                             Map<String, IValueConverter<T, K>> fieldValueConverter, boolean copyCollection) throws Exception {
    Class<?> srcClazz = src.getClass();
    Class<?> destClazz = dest.getClass();

    Field[] fields = ReflectUtil.getAllField(srcClazz);
    boolean fieldNameMappingAvaliable = CollectionUtil.isNotEmpty(fieldNameMapping);
    boolean fieldValueConverterAvaliable = CollectionUtil.isNotEmpty(fieldValueConverter);
    for (Field field : fields) {
      String srcFieldName = field.getName();
      String destFieldName = field.getName();
      if (fieldNameMappingAvaliable) {
        String mappingName = fieldNameMapping.get(srcFieldName);
        if (null != mappingName) {
          destFieldName = mappingName;
        }
      }
      Field destField = ReflectUtil.getDeclaredField(destClazz, destFieldName);
      if (null != destField) {
        if (0 != (destField.getModifiers() & Modifier.FINAL)) {
          // don't copy final field
          continue;
        }

        if (!copyCollection
            && (Collection.class.isAssignableFrom(destField.getDeclaringClass()) || Collection.class
            .isAssignableFrom(field.getDeclaringClass()))) {
          continue;
        }
        Object value = ReflectUtil.getFieldValue(src, field);
        if (fieldValueConverterAvaliable) {
          IValueConverter converter = fieldValueConverter.get(srcFieldName);
          if (null != converter) {
            value = converter.convert(value);
          }
        }

        if (null != value) {
          ReflectUtil.setField(dest, destField, value);
        }
      }
    }
    return dest;
  }

  /**
   * Convert object to Optional to prevent NPE.
   * A typical usage is in invoking chain to avoid NPE.
   * <cdoe>
   * Optional<AnnotatedType[]> optional = toOptional(() -> {
   *   return String.class.getAnnotatedInterfaces().getClass().getAnnotatedInterfaces();
   * });
   * </cdoe>
   *
   * @param supplier
   *     the function
   * @param <T>
   *     the param type
   * @return the wrapped Optional
   */
  public static <T> Optional<T> toOptional(Supplier<T> supplier) {
    try {
      return Optional.ofNullable(supplier.get());
    } catch (NullPointerException npe) {
      return Optional.empty();
    }
  }
}
