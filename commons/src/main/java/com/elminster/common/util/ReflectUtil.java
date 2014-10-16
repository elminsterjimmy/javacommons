package com.elminster.common.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
      Class<?>[] interfaces = clazz.getInterfaces();
      if (null != interfaces) {
        for (Class<?> ife : interfaces) {
          Method[] ifMethods = getAllMethod(ife);
          CollectionUtil.mergeArray(methodList, ifMethods);
        }
      }
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
    int argsCnt = args.length;
    Class<?>[] classArgs = new Class<?>[argsCnt];
    for (int i = 0; i < argsCnt; i++) {
      classArgs[i] = args[i].getClass();
    }
    
    Method method = getDeclaredMethod(obj.getClass(), methodName, classArgs);
    
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
    Class<?>[] classes = new Class<?>[args.length];
    for (int i = 0; i < args.length; i++) {
      classes[i] = args[i].getClass();
    }
    return getDeclaredMethod(clazz, methodName, classes);
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
      String methodName, Class<?>[] args) {
    Method[] methods = getAllMethod(clazz);
    for (Method method : methods) {
      if (method.getName().equals(methodName)) {
        Class<?>[] argClasses = method.getParameterTypes();
        int argCount = argClasses.length;
        boolean checked = true;
        if (argClasses.length == args.length) {
          for (int i = 0; i< argCount; i++) {
            if (!checkParamClass(argClasses[i], args[i])) {
              checked = false;
              break;
            }
          }
        } else {
          checked = false;
        }
        if (checked) {
          return method;
        }
      }
    }
    return null;
  }
  
  /**
   * Check the actual class is same or assignable from expect class.
   * @param expectClass the expect class
   * @param actualClass the actual class
   * @return the actual class is same or assignable from expect class
   */
  private static boolean checkParamClass(Class<?> expectClass, Class<?> actualClass) {
    if (expectClass.isPrimitive()) {
      if (actualClass.isPrimitive()) {
        return expectClass == actualClass;
      } else {
        expectClass = getWrappedClass(expectClass);
        return expectClass.isAssignableFrom(actualClass);
      }
    } else if (expectClass.isArray()) {
      if (actualClass.isArray()) {
        return checkParamClass(expectClass.getComponentType(), actualClass.getComponentType());
      }
    } else {
      if (actualClass.isPrimitive()) {
        actualClass = getWrappedClass(actualClass);
      }
      return expectClass.isAssignableFrom(actualClass);
    }
    return false;
  }

  /**
   * Get the wrap class of primitive type.
   * @param primitive the primitive class
   * @return the wrapped class
   */
  private static Class<?> getWrappedClass(Class<?> primitive) {
    Class<?> c = primitive;
    if (Integer.TYPE == primitive) {
      c = Integer.class;
    } else if (Long.TYPE == primitive) {
      c = Long.class;
    } else if (Short.TYPE == primitive) {
      c = Short.class;
    } else if (Float.TYPE == primitive) {
      c = Float.class;
    } else if (Double.TYPE == primitive) {
      c = Double.class;
    } else if (Character.TYPE == primitive) {
      c = Character.class;
    } else if (Boolean.TYPE == primitive) {
      c = Boolean.class;
    } else if (Byte.TYPE == primitive) {
      c = Byte.class;
    }
    return c;
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

  /**
   * Get the constructor of specified class.
   * 
   * @param clazz
   *          the specified class name
   * @return the constructor
   * @throws SecurityException
   *           on error
   * @throws NoSuchMethodException
   *           on error
   */
  public static Constructor<?> getConstructor(Class<?> clazz)
      throws NoSuchMethodException, SecurityException {
    return getConstructor(clazz, null);
  }

  /**
   * Get the constructor of specified class.
   * 
   * @param clazz
   *          the specified class name
   * @param args
   *          the specified class' arguments type
   * @return the constructor
   * @throws SecurityException
   *           on error
   * @throws NoSuchMethodException
   *           on error
   */
  public static Constructor<?> getConstructor(Class<?> clazz, Class<?>[] args)
      throws NoSuchMethodException, SecurityException {
    Constructor<?> constructor;
    if (null == args) {
      constructor = clazz.getDeclaredConstructor();
    } else {
      constructor = clazz.getDeclaredConstructor(args);
    }
    return constructor;
  }

  /**
   * Create new instance of specified class from reflect.
   * 
   * @param clazz
   *          the class name
   * @return the new instance
   * @throws SecurityException
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  public static Object newInstanceViaReflect(Class<?> clazz)
      throws NoSuchMethodException, SecurityException, InstantiationException,
      IllegalAccessException, IllegalArgumentException,
      InvocationTargetException {
    Constructor<?> constructor = getConstructor(clazz);
    makeAccessible(constructor);
    return constructor.newInstance();
  }
  
  /**
   * Get the generic type from type.
   * @param type the type
   * @return the generic type
   * @throws ClassNotFoundException on error
   */
  public static Class<?>[] getGenericType(Type type) throws ClassNotFoundException {
    Class<?>[] rtn = null;
    if (type instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) type;
      Type[] actualTypes = pt.getActualTypeArguments();
      rtn = new Class<?>[actualTypes.length];
      for (int i = 0; i < actualTypes.length; i++) {
        Type actualType = actualTypes[i];
        String[] split = actualType.toString().split(StringConstants.SPACE);
        String className;
        if (split.length > 1) {
          className = split[1];
        } else {
          return null;
        }
        rtn[i] = Class.forName(className);
      }
    }
    return rtn;
  }
  
  /**
   * Get the generic return type of specified method.
   * @param method the method
   * @return the generic return type
   * @throws ClassNotFoundException on error
   */
  public static Class<?>[] getGenericReturnType(Method method) throws ClassNotFoundException {
    Type type = method.getGenericReturnType();
    return getGenericType(type);
  }
  
  /**
   * Get the generic parameter type of specified method
   * @param method the method
   * @param parIdx the parameter index
   * @return the generic parameter type
   * @throws ClassNotFoundException on error
   */
  public static Class<?>[] getGenericParamType(Method method, int parIdx) throws ClassNotFoundException {
    Type type = method.getGenericParameterTypes()[parIdx];
    return getGenericType(type);
  }
  
  /**
   * Get the generic type of specified field.
   * @param field the field
   * @return the generic type
   * @throws ClassNotFoundException on error
   */
  public static Class<?>[] getGenericFieldType(Field field) throws ClassNotFoundException {
    Type type = field.getGenericType();
    return getGenericType(type);
  }
}
