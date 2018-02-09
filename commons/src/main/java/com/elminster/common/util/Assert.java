package com.elminster.common.util;

import java.util.Collection;
import java.util.Map;

import com.elminster.common.constants.Constants.StringConstants;

/**
 * The assert utilities.
 * 
 * @author jgu
 * @version 1.0
 */
public abstract class Assert {

  /**
   * Assert the expression is true, otherwise throws AssertException with specified message.
   * 
   * @param expression
   *          the expression
   * @param message
   *          the showing message if the assert failed
   */
  public static void isTrue(boolean expression, String message) {
    if (!expression) {
      throw new AssertException(message);
    }
  }
  
  /**
   * Assert the expression is true, otherwise throws AssertException with specified message.
   * 
   * @param expression
   *          the expression
   * @param messageTemplate
   *          the showing message template if the assert failed
   * @param messageArgs
   *          the showing message args
   */
  public static void isTrue(boolean expression, String messageTemplate, Object... messageArgs) {
    isTrue(expression, String.format(messageTemplate, messageArgs));
  }

  /**
   * Assert the expression is true, otherwise throws AssertException with specified message.
   * 
   * @param expression
   *          the expression
   */
  public static void isTrue(boolean expression) {
    isTrue(expression, "[Assertion failed] - this expression must be true");
  }

  /**
   * Assert the object is null, otherwise throws AssertException with specified message.
   * 
   * @param object
   *          the object
   * @param message
   *          the showing message if the assert failed
   */
  public static void isNull(Object object, String message) {
    if (null != object) {
      throw new AssertException(message);
    }
  }
  
  /**
   * Assert the object is null, otherwise throws AssertException with specified message.
   * 
   * @param expression
   *          the expression
   * @param messageTemplate
   *          the showing message template if the assert failed
   * @param messageArgs
   *          the showing message args
   */
  public static void isNull(boolean expression, String messageTemplate, Object... messageArgs) {
    isNull(expression, String.format(messageTemplate, messageArgs));
  }

  /**
   * Assert the object is null, otherwise throws AssertException with specified message.
   * 
   * @param object
   *          the object
   */
  public static void isNull(Object object) {
    isNull(object, "[Assertion failed] - the object argument must be null");
  }

  /**
   * Assert the object is not null, otherwise throws AssertException with specified message.
   * 
   * @param object
   *          the object
   * @param message
   *          the showing message if the assert failed
   */
  public static void notNull(Object object, String message) {
    if (null == object) {
      throw new AssertException(message);
    }
  }

  /**
   * Assert the object is not null, otherwise throws AssertException with specified message.
   * 
   * @param expression
   *          the expression
   * @param messageTemplate
   *          the showing message template if the assert failed
   * @param messageArgs
   *          the showing message args
   */
  public static void notNull(boolean expression, String messageTemplate, Object... messageArgs) {
    notNull(expression, String.format(messageTemplate, messageArgs));
  }


  /**
   * Assert the object is not null, otherwise throws AssertException with specified message.
   * 
   * @param object
   *          the object
   */
  public static void notNull(Object object) {
    notNull(object, "[Assertion failed] - this argument is required; it must not be null");
  }

  /**
   * Assert the text is not empty, otherwise throws AssertException with specified message.
   * 
   * @param text
   *          the text
   * @param message
   *          the showing message if the assert failed
   */
  public static void notEmpty(String text, String message) {
    if (StringUtil.isEmpty(text)) {
      throw new AssertException(message);
    }
  }

  /**
   * Assert the object is not empty, otherwise throws AssertException with specified message.
   * 
   * @param expression
   *          the expression
   * @param messageTemplate
   *          the showing message template if the assert failed
   * @param messageArgs
   *          the showing message args
   */
  public static void notEmpty(boolean expression, String messageTemplate, Object... messageArgs) {
    notEmpty(expression, String.format(messageTemplate, messageArgs));
  }

  /**
   * Assert the text is not empty, otherwise throws AssertException with specified message.
   * 
   * @param text
   *          the text
   */
  public static void notEmpty(String text) {
    notEmpty(text, "[Assertion failed] - this String argument must have length; it must not be null or empty");
  }

  /**
   * Assert the text is not blank, otherwise throws AssertException with specified message.
   * 
   * @param text
   *          the text
   * @param message
   *          the showing message if the assert failed
   */
  public static void notBlank(String text, String message) {
    if (StringUtil.isBlank(text)) {
      throw new AssertException(message);
    }
  }

  /**
   * Assert the object is not blank, otherwise throws AssertException with specified message.
   * 
   * @param expression
   *          the expression
   * @param messageTemplate
   *          the showing message template if the assert failed
   * @param messageArgs
   *          the showing message args
   */
  public static void notBlank(boolean expression, String messageTemplate, Object... messageArgs) {
    notBlank(expression, String.format(messageTemplate, messageArgs));
  }

  /**
   * Assert the text is not blank, otherwise throws AssertException with specified message.
   * 
   * @param text
   *          the text
   */
  public static void notBlank(String text) {
    notBlank(text, "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
  }

  /**
   * Assert that the given text does not contain the given substring.
   * 
   * @param textToSearch
   *          the text to search
   * @param substring
   *          the substring to find within the text
   * @param message
   *          the exception message to use if the assertion fails
   */
  public static void doesNotContain(String textToSearch, String substring, String message) {
    if (StringUtil.isNotEmpty(textToSearch) && StringUtil.isNotEmpty(substring) && textToSearch.contains(substring)) {
      throw new AssertException(message);
    }
  }
  
  /**
   * Assert that the given text does not contain the given substring.
   * 
   * <pre class="code">
   * Assert.doesNotContain(name, &quot;rod&quot;);
   * </pre>
   * 
   * @param textToSearch
   *          the text to search
   * @param substring
   *          the substring to find within the text
   */
  public static void doesNotContain(String textToSearch, String substring) {
    doesNotContain(textToSearch, substring,
        "[Assertion failed] - this String argument must not contain the substring [" + substring + "]");
  }

  /**
   * Assert the array is not empty, otherwise throws AssertException with specified message.
   * 
   * @param array
   *          the array
   * @param message
   *          the showing message if the assert failed
   */
  public static void notEmpty(Object[] array, String message) {
    if (ArrayUtil.isArrayEmpty(array)) {
      throw new AssertException(message);
    }
  }

  /**
   * Assert the array is not empty, otherwise throws AssertException with specified message.
   * 
   * @param array
   *          the array
   */
  public static void notEmpty(Object[] array) {
    notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
  }

  /**
   * Assert that an array has no null elements. Note: Does not complain if the array is empty!
   * 
   * @param array
   *          the array to check
   * @param message
   *          the exception message to use if the assertion fails
   */
  public static void noNullElements(Object[] array, String message) {
    if (null != array) {
      for (Object element : array) {
        if (null == element) {
          throw new AssertException(message);
        }
      }
    }
  }

  /**
   * Assert that an array has no null elements. Note: Does not complain if the array is empty!
   * 
   * @param array
   *          the array to check
   */
  public static void noNullElements(Object[] array) {
    noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
  }

  /**
   * Assert that a collection has elements; that is, it must not be {@code null} and must have at least one element.
   * 
   * @param collection
   *          the collection to check
   * @param message
   *          the exception message to use if the assertion fails
   */
  public static void notEmpty(Collection<?> collection, String message) {
    if (CollectionUtil.isEmpty(collection)) {
      throw new AssertException(message);
    }
  }

  /**
   * Assert that a collection has elements; that is, it must not be {@code null} and must have at least one element.
   * 
   * @param collection
   *          the collection to check
   */
  public static void notEmpty(Collection<?> collection) {
    notEmpty(collection, "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
  }

  /**
   * Assert that a Map has entries; that is, it must not be {@code null} and must have at least one entry.
   * 
   * @param map
   *          the map to check
   * @param message
   *          the exception message to use if the assertion fails
   */
  public static void notEmpty(Map<?, ?> map, String message) {
    if (CollectionUtil.isEmpty(map)) {
      throw new AssertException(message);
    }
  }

  /**
   * Assert that a Map has entries; that is, it must not be {@code null} and must have at least one entry.
   * 
   * @param map
   *          the map to check
   */
  public static void notEmpty(Map<?, ?> map) {
    notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
  }

  /**
   * Assert that the provided object is an instance of the provided class.
   * 
   * @param clazz
   *          the required class
   * @param obj
   *          the object to check
   */
  public static void isInstanceOf(Class<?> clazz, Object obj) {
    isInstanceOf(clazz, obj, StringConstants.EMPTY_STRING);
  }

  /**
   * Assert that the provided object is an instance of the provided class.
   * 
   * @param type
   *          the type to check against
   * @param obj
   *          the object to check
   * @param message
   *          a message which will be prepended to the message produced by the function itself, and which may be used to
   *          provide context. It should normally end in a ": " or ". " so that the function generate message looks ok
   *          when prepended to it.
   */
  public static void isInstanceOf(Class<?> type, Object obj, String message) {
    notNull(type, "Type to check against must not be null");
    if (!type.isInstance(obj)) {
      throw new AssertException((StringUtil.isNotEmpty(message) ? message + " " : "") + "Object of class ["
          + (obj != null ? obj.getClass().getName() : "null") + "] must be an instance of " + type);
    }
  }

  /**
   * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
   * 
   * 
   * @param superType
   *          the super type to check
   * @param subType
   *          the sub type to check
   */
  public static void isAssignable(Class<?> superType, Class<?> subType) {
    isAssignable(superType, subType, StringConstants.EMPTY_STRING);
  }

  /**
   * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
   * 
   * @param superType
   *          the super type to check against
   * @param subType
   *          the sub type to check
   * @param message
   *          a message which will be prepended to the message produced by the function itself, and which may be used to
   *          provide context. It should normally end in a ": " or ". " so that the function generate message looks ok
   *          when prepended to it.
   */
  public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
    notNull(superType, "Type to check against must not be null");
    if (subType == null || !superType.isAssignableFrom(subType)) {
      throw new AssertException(message + subType + " is not assignable to " + superType);
    }
  }
}
