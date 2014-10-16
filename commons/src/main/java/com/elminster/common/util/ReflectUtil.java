package com.elminster.common.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import com.elminster.common.constants.Constants.StringConstants;

/**
 * Reflect Utilities
 * 
 * @author Gu
 * @version 1.0
 * 
 */
public abstract class ReflectUtil {

  /**
   * Get all interfaces the specified class implemented.
   * 
   * @param clazz
   *          the specified class
   * @return interfaces the specified class implemented
   */
  public static Class<?>[] getAllInterface(Class<? extends Object> clazz) {
    List<Class<?>> interfaceList = new ArrayList<Class<?>>();
    do {
      Class<?>[] interfaces = clazz.getInterfaces();
      CollectionUtil.mergeArray(interfaceList, interfaces);
      clazz = clazz.getSuperclass();
    } while (null != clazz);
    return (Class<?>[]) CollectionUtil.collection2Array(interfaceList);
  }

  /**
   * Get all fields the specified class declared (include the declared field in
   * super class).
   * 
   * @param clazz
   *          the specified class
   * @return fields the specified class declared
   */
  public static Field[] getAllField(Class<? extends Object> clazz) {
    List<Field> fieldList = new ArrayList<Field>();
    do {
      Field[] fields = clazz.getDeclaredFields();
      CollectionUtil.mergeArray(fieldList, fields);
      clazz = clazz.getSuperclass();
    } while (null != clazz);
    return (Field[]) CollectionUtil.collection2Array(fieldList);
  }

  /**
   * Get all methods the specified class declared (include the declared methods
   * in super class).
   * 
   * @param clazz
   *          the specified class
   * @return methods the specified class declared
   */
  public static Method[] getAllMethod(Class<? extends Object> clazz) {
    List<Method> methodList = new ArrayList<Method>();
    do {
      Method[] methods = clazz.getDeclaredMethods();
      CollectionUtil.mergeArray(methodList, methods);
      clazz = clazz.getSuperclass();
    } while (null != clazz);
    return (Method[]) CollectionUtil.collection2Array(methodList);
  }

  /**
   * Make the specified method accessible.
   * 
   * @param method
   *          the specified method
   */
  public static void makeAccessible(Method method) {
    if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method
        .getDeclaringClass().getModifiers())) && !method.isAccessible()) {
      method.setAccessible(true);
    }
  }

  /**
   * Make the specified constructor accessible.
   * 
   * @param method
   *          the specified constructor
   */
  public static void makeAccessible(Constructor<?> constructor) {
    if ((!Modifier.isPublic(constructor.getModifiers()) || !Modifier
        .isPublic(constructor.getDeclaringClass().getModifiers()))
        && !constructor.isAccessible()) {
      constructor.setAccessible(true);
    }
  }

  /**
   * Make the specified field accessible.
   * 
   * @param method
   *          the specified field
   */
  public static void makeAccessible(Field field) {
    if (!Modifier.isPublic(field.getModifiers()) && !field.isAccessible()) {
      field.setAccessible(true);
    }
  }

  /**
   * Invoke the specified method in runtime
   * 
   * @param the
   *          object the underlying method is invoked from
   * @param methodName
   *          specified method name
   * @param args
   *          specified method arguments
   * @return method result
   * @throws NoSuchMethodException
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   */
  public static Object invoke(Object obj, String methodName, Object[] args)
      throws NoSuchMethodException, IllegalArgumentException,
      IllegalAccessException, InvocationTargetException {
    Method method = getDeclaredMethod(obj.getClass(), methodName, args);
    if (null == method) {
      throw new NoSuchMethodException();
    }
    return invoke(obj, method, args);
  }

  /**
   * Invoke the specified method in runtime
   * 
   * @param the
   *          object the underlying method is invoked from
   * @param methodName
   *          specified method
   * @param args
   *          specified method arguments
   * @return method result
   * @throws NoSuchMethodException
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   */
  public static Object invoke(Object obj, Method method, Object[] args)
      throws IllegalArgumentException, IllegalAccessException,
      InvocationTargetException {
    makeAccessible(method);
    return method.invoke(obj, args);
  }

  /**
   * Set the specified field to a specified new value.
   * 
   * @param obj
   *          the object whose field should be modified
   * @param fieldName
   *          specified field's name
   * @param value
   *          new value
   */
  public static void setField(Object obj, String fieldName, Object value)
      throws IllegalArgumentException, IllegalAccessException {
    Class<?> clazz = obj.getClass();
    Field field = getDeclaredField(clazz, fieldName);
    makeAccessible(field);
    field.set(obj, value);
  }

  /**
   * Set the specified field to a specified new value in runtime.
   * 
   * @param obj
   *          the object whose field should be modified
   * @param field
   *          specified field
   * @param value
   *          new value
   */
  public static void setField(Object obj, Field field, Object value)
      throws IllegalArgumentException, IllegalAccessException {
    makeAccessible(field);
    field.set(obj, value);
  }

  /**
   * Get the specified field value in runtime.
   * 
   * @param obj
   *          object from which the represented field's value is to be extracted
   * @param fieldName
   *          specified field's name
   * @return the value of the represented field in object
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   */
  public static Object getFieldValue(Object obj, String fieldName)
      throws IllegalArgumentException, IllegalAccessException {
    Class<?> clazz = obj.getClass();
    Field field = getDeclaredField(clazz, fieldName);
    return getFieldValue(obj, field);
  }

  /**
   * Get the specified field value in runtime.
   * 
   * @param obj
   *          object from which the represented field's value is to be extracted
   * @param fieldName
   *          specified field
   * @return the value of the represented field in object
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   */
  public static Object getFieldValue(Object obj, Field field)
      throws IllegalArgumentException, IllegalAccessException {
    Object value = null;
    String getMethod = "get" + StringUtil.capitalize(field.getName()); //$NON-NLS-1$
    try {
      value = invoke(obj, getMethod, new Object[] {});
    } catch (NoSuchMethodException e) {
    } catch (InvocationTargetException e) {
    }
    if (null == value && field.getType().equals(Boolean.TYPE)
        || field.getType().equals(Boolean.class)) {
      getMethod = "is" + StringUtil.capitalize(field.getName()); //$NON-NLS-1$
      try {
        value = invoke(obj, getMethod, new Object[] {});
      } catch (NoSuchMethodException e) {
      } catch (InvocationTargetException e) {
      }
    }
    if (null == value) {
      makeAccessible(field);
      value = field.get(obj);
    }
    return value;
  }

  /**
   * Get the specified declared method by method name
   * 
   * @param clazz
   *          the class declared the specified method
   * @param methodName
   *          the specified method's name
   * @param args
   *          the specified method's arguments type
   * @return the specified declared method
   */
  public static Method getDeclaredMethod(Class<? extends Object> clazz,
      String methodName, Object[] args) {
    Method[] methods = getAllMethod(clazz);
    for (Method method : methods) {
      if (method.getName().equals(methodName)) {
        Class<?>[] argClasses = method.getParameterTypes();
        if (argClasses.length == args.length) {
          int argCount = argClasses.length;
          boolean isChecked = true;
          for (int i = 0; i < argCount; i++) {
            if (argClasses[i].isPrimitive()) {
              if (!checkPrimitive(argClasses[i], args[i])) {
                isChecked = false;
                break;
              }
            } else {
              if (!argClasses[i].isInstance(args[i])) {
                isChecked = false;
                break;
              }
            }
          }
          if (isChecked) {
            return method;
          }
        }
      }
    }
    return null;
  }

  /**
   * Check Class equal if the clazz is primitive
   * 
   * @param clazz
   *          the primitive class
   * @param object
   *          the object to check
   * @return the object is the type of the specified primitive class
   */
  private static boolean checkPrimitive(Class<?> clazz, Object object) {
    if (object.getClass().isPrimitive()) {
      return clazz == object.getClass();
    }
    Class<?> c = clazz;
    if (Integer.TYPE == clazz) {
      c = Integer.class;
    } else if (Long.TYPE == clazz) {
      c = Long.class;
    } else if (Short.TYPE == clazz) {
      c = Short.class;
    } else if (Float.TYPE == clazz) {
      c = Float.class;
    } else if (Double.TYPE == clazz) {
      c = Double.class;
    } else if (Character.TYPE == clazz) {
      c = Character.class;
    } else if (Boolean.TYPE == clazz) {
      c = Boolean.class;
    }
    return c.isInstance(object);
  }

  /**
   * Get the specified declared field by field name
   * 
   * @param clazz
   *          the class declared the specified field
   * @param fieldName
   *          the specified field's name
   * @return the specified declared field
   */
  public static Field getDeclaredField(Class<?> clazz, String fieldName) {
    return getDeclearedField(clazz, fieldName, null);
  }

  /**
   * Get the specified declared field by field name
   * 
   * @param clazz
   *          the class declared the specified field
   * @param fieldName
   *          the specified field's name
   * @param type
   *          the specified field's type
   * @return the specified declared field
   */
  public static Field getDeclearedField(Class<?> clazz, String fieldName,
      Class<?> type) {
    Field[] fields = getAllField(clazz);
    for (Field field : fields) {
      if (field.getName().equals(fieldName)
          && (null == type || field.getType().equals(type))) {
        return field;
      }
    }
    return null;
  }

  /**
   * Get the method name for a depth in call stack.
   * 
   * @param depth
   *          depth in the call stack (0 means current method, 1 means call
   *          method, ...)
   * @return method name
   */
  public static String getCallMethodName(int depth) {
    StackTraceElement[] stes = new Throwable().getStackTrace();
    StackTraceElement ste = stes[depth];
    return ste.getClassName() + StringConstants.SHARP + ste.getMethodName();
  }
}
