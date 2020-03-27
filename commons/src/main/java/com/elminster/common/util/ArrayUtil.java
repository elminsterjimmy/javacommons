package com.elminster.common.util;

import java.lang.reflect.Array;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.elminster.common.constants.Constants.StringConstants;
import com.elminster.common.util.Messages.Message;
import org.apache.commons.lang3.ArrayUtils;

/**
 * The array utilites.
 * 
 * @author jgu
 * @version 1.0
 */
abstract public class ArrayUtil {

  /** the default join string <code>,</code>. */
  private static final String DEFAULT_JOIN_STRING = StringConstants.COMMA;
  /** Start of an Array: <code>[</code> */
  private static final String ARRAY_START = StringConstants.LEFT_SQUARE_BRACKETS;
  /** End of an Array: <code>]</code> */
  private static final String ARRAY_END = StringConstants.RIGHT_SQUARE_BRACKETS;
  /** Empty Array: <code>[]</code> */
  private static final String EMPTY_ARRAY = ARRAY_START + ARRAY_END;
  /** Array Elements' separator: <code>, </code> */
  private static final String ARRAY_ELEMENT_SEPARATOR = StringConstants.COMMA + StringConstants.SPACE;
  /** Array's address: <code>@ </code> */
  private static final String ARRAY_ADDRESS = StringConstants.AT;

  /**
   * Reallocates an array with a new size, and copies the contents of the old array to the new array.
   * 
   * @param array
   *          the old array, to be reallocated.
   * @param newSize
   *          the new array size.
   * @return A new array with the same contents.
   */
  public static Object resize(final Object[] array, final int newSize) {
    if (newSize < 0) {
      throw new IllegalArgumentException("size cannot be negative.");
    }
    int oldSize = array.length;
    Class<?> elementType = array.getClass().getComponentType();
    Object newArray = Array.newInstance(elementType, newSize);
    int preserveLength = Math.min(oldSize, newSize);
    if (preserveLength > 0) {
      System.arraycopy(array, 0, newArray, 0, preserveLength);
    }
    return newArray;
  }

  /**
   * Join specified arrays to a new array
   * 
   * @param o1
   *          the fist array to join
   * @param o2
   *          the second array to join
   * @return a new array contains all elements in specified arrays
   */
  public static Object[] joinArray(final Object[] o1, final Object[] o2) {
    if (ArrayUtil.isArrayEmpty(o1)) {
      return o2;
    }
    if (ArrayUtil.isArrayEmpty(o2)) {
      return o1;
    }
    Class<?> type1 = o1.getClass().getComponentType();
    Class<?> type2 = o2.getClass().getComponentType();
    if (!type1.equals(type2)) {
      throw new IllegalArgumentException(Messages.getString(Message.ARRAY_TYPE_UNMATCH, new Object[] { type1.toString(), type2.toString() }));
    }
    Object[] result = (Object[]) Array.newInstance(type1, o1.length + o2.length);
    System.arraycopy(o1, 0, result, 0, o1.length);
    System.arraycopy(o2, 0, result, o1.length, o2.length);
    return result;
  }

  /**
   * Is array empty?
   * 
   * @param array
   *          the array
   * @return is empty?
   */
  public static boolean isArrayEmpty(final Object[] array) {
    return null == array || 0 == array.length;
  }

  /**
   * Is array not empty?
   * 
   * @param array
   *          the array
   * @return is not empty?
   */
  public static boolean isArrayNotEmpty(final Object[] array) {
    return !isArrayEmpty(array);
  }

  /**
   * Slice the array from start.
   * 
   * @param array
   *          the array
   * @param start
   *          the start
   * @return the sliced array
   */
  public static Object[] slice(final Object[] array, final int start) {
    return slice(array, start, array.length);
  }

  /**
   * Slice the array with the range [start, end).
   * 
   * @param array
   *          the array
   * @param start
   *          the slice start index
   * @param end
   *          the slice end index
   * @return the sliced array
   */
  public static Object[] slice(final Object[] array, final int start, final int end) {
    if (null == array) {
      return null;
    }
    if (start > end) {
      throw new IndexOutOfBoundsException("start should not great than end.");
    }
    Class<?> type = array.getClass().getComponentType();
    int length = end - start;
    Object[] result = (Object[]) Array.newInstance(type, length);
    System.arraycopy(array, start, result, 0, length);
    return result;
  }

  /**
   * Join the array with <code>,</code>.
   * 
   * @param array
   *          the array
   * @return the joined string
   */
  public static String joinString(final Object[] array) {
    return joinString(array, DEFAULT_JOIN_STRING);
  }

  /**
   * Join the array with specified string.
   * 
   * @param array
   *          the array
   * @param joinString
   *          the join string
   * @return the joined string
   */
  public static String joinString(final Object[] array, final String joinString) {
    StringJoiner joiner = new StringJoiner(joinString);
    if (null != array) {
      Stream.of(array).forEach(e -> joiner.add(e.toString()));
    }
    return joiner.toString();
  }

  /**
   * Revert the array.
   * 
   * @param array
   *          the array
   * @return the reverted array
   */
  public static Object[] revertArray(final Object[] array) {
    if (null == array) {
      return null;
    }
    Class<?> type = array.getClass().getComponentType();
    int length = array.length;
    Object[] result = (Object[]) Array.newInstance(type, length);
    for (int i = 0; i < length; i++) {
      result[i] = array[length - 1 - i];
    }
    return result;
  }

  /**
   * Check whether the specified array contains the specified element
   * 
   * @param array
   *          the array to check
   * @param element
   *          the element to check for
   * @return whether the element is in the array
   */
  public static <T> boolean contains(T[] array, T element) {
    if (null == array) {
      throw new IllegalArgumentException(Messages.getString(Message.TARGET_ARRAY_IS_NULL));
    }
    for (T obj : array) {
      if (ObjectUtil.isEqual(obj, element)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Return a String representation of the specified array's elements
   *
   * @param array
   *          the array to build a String representation for
   * @return a String representation of the array's elements
   */
  public static String toString(short[] array) {
    if (null == array) {
      return StringConstants.EMPTY_STRING;
    }
    int length = array.length;
    if (0 == length) {
      return EMPTY_ARRAY;
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      if (0 == i) {
        sb.append(ARRAY_START);
      } else {
        sb.append(ARRAY_ELEMENT_SEPARATOR);
      }
      sb.append(array[i]);
    }
    sb.append(ARRAY_END);
    sb.append(ARRAY_ADDRESS);
    sb.append(array.toString());
    return sb.toString();
  }

  /**
   * Return a String representation of the specified array's elements
   *
   * @param array
   *          the array to build a String representation for
   * @return a String representation of the array's elements
   */
  public static String toString(long[] array) {
    if (null == array) {
      return StringConstants.EMPTY_STRING;
    }
    int length = array.length;
    if (0 == length) {
      return EMPTY_ARRAY;
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      if (0 == i) {
        sb.append(ARRAY_START);
      } else {
        sb.append(ARRAY_ELEMENT_SEPARATOR);
      }
      sb.append(array[i]);
    }
    sb.append(ARRAY_END);
    sb.append(ARRAY_ADDRESS);
    sb.append(array.toString());
    return sb.toString();
  }

  /**
   * Return a String representation of the specified array's elements
   *
   * @param array
   *          the array to build a String representation for
   * @return a String representation of the array's elements
   */
  public static String toString(int[] array) {
    if (null == array) {
      return StringConstants.EMPTY_STRING;
    }
    int length = array.length;
    if (0 == length) {
      return EMPTY_ARRAY;
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      if (0 == i) {
        sb.append(ARRAY_START);
      } else {
        sb.append(ARRAY_ELEMENT_SEPARATOR);
      }
      sb.append(array[i]);
    }
    sb.append(ARRAY_END);
    sb.append(ARRAY_ADDRESS);
    sb.append(array.toString());
    return sb.toString();
  }

  /**
   * Return a String representation of the specified array's elements
   *
   * @param array
   *          the array to build a String representation for
   * @return a String representation of the array's elements
   */
  public static String toString(float[] array) {
    if (null == array) {
      return StringConstants.EMPTY_STRING;
    }
    int length = array.length;
    if (0 == length) {
      return EMPTY_ARRAY;
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      if (0 == i) {
        sb.append(ARRAY_START);
      } else {
        sb.append(ARRAY_ELEMENT_SEPARATOR);
      }
      sb.append(array[i]);
    }
    sb.append(ARRAY_END);
    sb.append(ARRAY_ADDRESS);
    sb.append(array.toString());
    return sb.toString();
  }

  /**
   * Return a String representation of the specified array's elements
   *
   * @param array
   *          the array to build a String representation for
   * @return a String representation of the array's elements
   */
  public static String toString(double[] array) {
    if (null == array) {
      return StringConstants.EMPTY_STRING;
    }
    int length = array.length;
    if (0 == length) {
      return EMPTY_ARRAY;
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      if (0 == i) {
        sb.append(ARRAY_START);
      } else {
        sb.append(ARRAY_ELEMENT_SEPARATOR);
      }
      sb.append(array[i]);
    }
    sb.append(ARRAY_END);
    sb.append(ARRAY_ADDRESS);
    sb.append(array.toString());
    return sb.toString();
  }

  /**
   * Return a String representation of the specified array's elements
   *
   * @param array
   *          the array to build a String representation for
   * @return a String representation of the array's elements
   */
  public static String toString(char[] array) {
    if (null == array) {
      return StringConstants.EMPTY_STRING;
    }
    int length = array.length;
    if (0 == length) {
      return EMPTY_ARRAY;
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      if (0 == i) {
        sb.append(ARRAY_START);
      } else {
        sb.append(ARRAY_ELEMENT_SEPARATOR);
      }
      sb.append(array[i]);
    }
    sb.append(ARRAY_END);
    sb.append(ARRAY_ADDRESS);
    sb.append(array.toString());
    return sb.toString();
  }

  /**
   * Return a String representation of the specified array's elements
   *
   * @param array
   *          the array to build a String representation for
   * @return a String representation of the array's elements
   */
  public static String toString(byte[] array) {
    if (null == array) {
      return StringConstants.EMPTY_STRING;
    }
    int length = array.length;
    if (0 == length) {
      return EMPTY_ARRAY;
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      if (0 == i) {
        sb.append(ARRAY_START);
      } else {
        sb.append(ARRAY_ELEMENT_SEPARATOR);
      }
      sb.append(array[i]);
    }
    sb.append(ARRAY_END);
    sb.append(ARRAY_ADDRESS);
    sb.append(array.toString());
    return sb.toString();
  }

  /**
   * Return a String representation of the specified array's elements
   *
   * @param array
   *          the array to build a String representation for
   * @return a String representation of the array's elements
   */
  public static String toString(boolean[] array) {
    if (null == array) {
      return StringConstants.EMPTY_STRING;
    }
    int length = array.length;
    if (0 == length) {
      return EMPTY_ARRAY;
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      if (0 == i) {
        sb.append(ARRAY_START);
      } else {
        sb.append(ARRAY_ELEMENT_SEPARATOR);
      }
      sb.append(array[i]);
    }
    sb.append(ARRAY_END);
    sb.append(ARRAY_ADDRESS);
    sb.append(array.toString());
    return sb.toString();
  }

  /**
   * Return a String representation of the specified array's elements
   *
   * @param array
   *          the array to build a String representation for
   * @return a String representation of the array's elements
   */
  public static String toString(Object[] array) {
    if (null == array) {
      return StringConstants.EMPTY_STRING;
    }
    int length = array.length;
    if (0 == length) {
      return EMPTY_ARRAY;
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      if (0 == i) {
        sb.append(ARRAY_START);
      } else {
        sb.append(ARRAY_ELEMENT_SEPARATOR);
      }
      sb.append(array[i]);
    }
    sb.append(ARRAY_END);
    sb.append(ARRAY_ADDRESS);
    sb.append(array.toString());
    return sb.toString();
  }
}
