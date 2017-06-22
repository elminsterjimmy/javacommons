package com.elminster.common.util;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.elminster.common.constants.Constants.StringConstants;

/**
 * Reflect Utilities
 * 
 * @author Gu
 * @version 1.0
 * 
 */
public abstract class ReflectUtil {

  private static final Map<Class<?>, Class<?>> primitive2WrapperMap = new HashMap<>(9);
  private static final Map<Class<?>, Class<?>> wrapper2PrimitiveMap = new HashMap<>(9);
  private static final Map<String, Class<?>> primitiveTypeNameMap = new HashMap<>(17);

  /** Suffix for array class names: "[]" */
  public static final String ARRAY_SUFFIX = "[]";
  /** Prefix for internal array class names: "[L" */
  private static final String INTERNAL_ARRAY_PREFIX = "[L";

  static {
    primitive2WrapperMap.put(Boolean.TYPE, Boolean.class);
    primitive2WrapperMap.put(Byte.TYPE, Byte.class);
    primitive2WrapperMap.put(Character.TYPE, Character.class);
    primitive2WrapperMap.put(Short.TYPE, Short.class);
    primitive2WrapperMap.put(Integer.TYPE, Integer.class);
    primitive2WrapperMap.put(Long.TYPE, Long.class);
    primitive2WrapperMap.put(Double.TYPE, Double.class);
    primitive2WrapperMap.put(Float.TYPE, Float.class);
    primitive2WrapperMap.put(Void.TYPE, Void.TYPE);

    for (final Class<?> primitiveClass : primitive2WrapperMap.keySet()) {
      final Class<?> wrapperClass = primitive2WrapperMap.get(primitiveClass);
      if (!primitiveClass.equals(wrapperClass)) {
        wrapper2PrimitiveMap.put(wrapperClass, primitiveClass);
      }
    }

    Set<Class<?>> primitiveTypeClasses = new HashSet<Class<?>>(17);
    primitiveTypeClasses.addAll(primitive2WrapperMap.keySet());
    // @formatter:off
    primitiveTypeClasses.addAll(Arrays
        .asList(new Class<?>[] { 
          boolean[].class,
          byte[].class,
          char[].class,
          double[].class, float[].class,
          int[].class,
          long[].class,
          short[].class }));
    // @formatter:on
    for (Class<?> clazz : primitiveTypeClasses) {
      primitiveTypeNameMap.put(clazz.getName(), clazz);
    }
  }

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
   * Get all fields the specified class declared (include the declared field in super class).
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
   * Get all methods the specified class declared (include the declared methods in super class).
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
    if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
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
    if ((!Modifier.isPublic(constructor.getModifiers()) || !Modifier.isPublic(constructor.getDeclaringClass().getModifiers())) && !constructor.isAccessible()) {
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
  public static Object invoke(Object obj, String methodName, Object... args)
      throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    if (null == args) {
      args = new Object[] {};
    }
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
  public static Object invoke(Object obj, Method method, Object... args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
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
  public static void setField(Object obj, String fieldName, Object value) throws IllegalArgumentException, IllegalAccessException {
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
  public static void setField(Object obj, Field field, Object value) throws IllegalArgumentException, IllegalAccessException {
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
  public static Object getFieldValue(Object obj, String fieldName) throws IllegalArgumentException, IllegalAccessException {
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
  public static Object getFieldValue(Object obj, Field field) throws IllegalArgumentException, IllegalAccessException {
    Object value = null;
    String getMethod = "get" + StringUtil.capitalize(field.getName()); //$NON-NLS-1$
    try {
      value = invoke(obj, getMethod, new Object[] {});
    } catch (NoSuchMethodException e) {
    } catch (InvocationTargetException e) {
    }
    if (null == value && field.getType().equals(Boolean.TYPE) || field.getType().equals(Boolean.class)) {
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
  public static Method getDeclaredMethod(Class<? extends Object> clazz, String methodName, Object... args) {
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
   * @return the specified declared method
   */
  public static Method getDeclaredMethod(Class<? extends Object> clazz, String methodName) {
    return getDeclaredMethod(clazz, methodName, new Class<?>[0]);
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
  public static Method getDeclaredMethod(Class<? extends Object> clazz, String methodName, Class<?>... args) {
    Method[] methods = getAllMethod(clazz);
    for (Method method : methods) {
      if (method.getName().equals(methodName)) {
        Class<?>[] argClasses = method.getParameterTypes();
        int argCount = argClasses.length;
        boolean checked = true;
        if (argClasses.length == args.length) {
          for (int i = 0; i < argCount; i++) {
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
   * 
   * @param expectClass
   *          the expect class
   * @param actualClass
   *          the actual class
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
   * 
   * @param primitive
   *          the primitive class
   * @return the wrapped class
   */
  public static Class<?> getWrappedClass(Class<?> primitive) {
    return primitive2WrapperMap.get(primitive);
  }

  /**
   * Check if a specified object can adapt to specified class.
   * 
   * @param clazz
   *          the class adapt to
   * @param obj
   *          the object need to adapt
   * @return whether the object can adapt to specified class
   */
  public static boolean isAdpatable(Class<?> clazz, Object obj) {
    Assert.notNull(clazz);
    boolean isPrimitive = clazz.isPrimitive();
    if (null == obj) {
      return !isPrimitive;
    }

    if (isPrimitive) {
      clazz = getWrappedClass(clazz);
    }
    return clazz == obj.getClass() || clazz.isInstance(obj);
  }

  /**
   * Get the code base location of the class.
   * 
   * @param clazz
   *          the class
   * @return the code base of the class
   */
  public static URL getCodeBase(Class<?> clazz) {
    Assert.notNull(clazz);
    ProtectionDomain domain = clazz.getProtectionDomain();
    URL url = null;
    if (null != domain) {
      CodeSource source = domain.getCodeSource();
      if (null != source) {
        url = source.getLocation();
      }
    }
    return url;
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
  public static Field getDeclearedField(Class<?> clazz, String fieldName, Class<?> type) {
    Field[] fields = getAllField(clazz);
    for (Field field : fields) {
      if (field.getName().equals(fieldName) && (null == type || field.getType().equals(type))) {
        return field;
      }
    }
    return null;
  }

  /**
   * Get the method name for a depth in call stack.
   * 
   * @param depth
   *          depth in the call stack (0 means current method, 1 means call method, ...)
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
  public static Constructor<?> getConstructor(Class<?> clazz) throws NoSuchMethodException, SecurityException {
    return getConstructor(clazz, new Class<?>[0]);
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
  public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... args) throws NoSuchMethodException, SecurityException {
    Constructor<?> constructor;
    if (0 == args.length) {
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
   *          the class
   * @return the new instance
   * @throws SecurityException
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  public static Object newInstanceViaReflect(Class<?> clazz)
      throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    try {
      Constructor<?> constructor = getConstructor(clazz);
      makeAccessible(constructor);
      return constructor.newInstance();
    } catch (NoSuchMethodException nsme) {
      return clazz.newInstance();
    }
  }

  /**
   * Create new instance of specified class from reflect.
   * 
   * @param className
   *          the class name
   * @return the new instance
   * @throws SecurityException
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   * @throws InstantiationException
   * @throws ClassNotFoundException
   */
  public static Object newInstanceViaReflect(String className)
      throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
    Class<?> clazz = Class.forName(className);
    return newInstanceViaReflect(clazz);
  }

  /**
   * Same as <code>Class.forName()</code>, except that it works for primitive types.
   */
  public static Class<?> forName(String name) throws ClassNotFoundException {
    Assert.notNull(name);
    return forName(name, ReflectUtil.class.getClassLoader());
  }

  /**
   * Replacement for <code>Class.forName()</code> that also returns Class instances for primitives (like "int") and array class names (like "String[]").
   * 
   * @param name
   *          the name of the Class
   * @param classLoader
   *          the class loader to use (may be <code>null</code>, which indicates the default class loader)
   * @return Class instance for the supplied name
   * @throws ClassNotFoundException
   *           if the class was not found
   * @throws LinkageError
   *           if the class file could not be loaded
   * @see Class#forName(String, boolean, ClassLoader)
   */
  public static Class<?> forName(String name, ClassLoader classLoader) throws ClassNotFoundException, LinkageError {

    Class<?> clazz = resolvePrimitiveClassName(name);
    if (null != clazz) {
      return clazz;
    }

    // "java.lang.String[]" style arrays
    if (name.endsWith(ARRAY_SUFFIX)) {
      String elementClassName = name.substring(0, name.length() - ARRAY_SUFFIX.length());
      Class<?> elementClass = forName(elementClassName, classLoader);
      return Array.newInstance(elementClass, 0).getClass();
    }

    // "[Ljava.lang.String;" style arrays
    int internalArrayMarker = name.indexOf(INTERNAL_ARRAY_PREFIX);
    if (internalArrayMarker != -1 && name.endsWith(";")) {
      String elementClassName = null;
      if (internalArrayMarker == 0) {
        elementClassName = name.substring(INTERNAL_ARRAY_PREFIX.length(), name.length() - 1);
      } else if (name.startsWith("[")) {
        elementClassName = name.substring(1);
      }
      Class<?> elementClass = forName(elementClassName, classLoader);
      return Array.newInstance(elementClass, 0).getClass();
    }

    ClassLoader classLoaderToUse = classLoader;
    if (null == classLoaderToUse) {
      classLoaderToUse = ReflectUtil.class.getClassLoader();
    }
    return classLoaderToUse.loadClass(name);
  }

  /**
   * Resolve the given class name as primitive class, if appropriate, according to the JVM's naming rules for primitive classes.
   * <p>
   * Also supports the JVM's internal class names for primitive arrays. Does <i>not</i> support the "[]" suffix notation for primitive arrays; this is only supported by
   * {@link #forName}.
   * 
   * @param name
   *          the name of the potentially primitive class
   * @return the primitive class, or <code>null</code> if the name does not denote a primitive class or primitive array class
   */
  public static Class<?> resolvePrimitiveClassName(String name) {
    Class<?> result = null;
    // Most class names will be quite long, considering that they
    // SHOULD sit in a package, so a length check is worthwhile.
    if (name != null && name.length() <= 8) {
      // Could be a primitive - likely.
      result = (Class<?>) primitiveTypeNameMap.get(name);
    }
    return result;
  }

}
