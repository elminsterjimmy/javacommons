package com.elminster.common.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.elminster.common.constants.Constants.StringConstants;

/**
 * The Bytecode Utilities.
 * 
 * @author jinggu
 * @version 1.0
 */
abstract public class BytecodeUtil {

  private static final ConcurrentHashMap<String, Class<?>> BYTECODE_CLASS_CACHE = new ConcurrentHashMap<>();
  private static final Map<Class<?>, Character> primitive2JVMBytecodeMap = new HashMap<>(9);

  public static final char JVM_BYTECODE_VOID = 'V';
  public static final char JVM_BYTECODE_BOOLEAN = 'Z';
  public static final char JVM_BYTECODE_BYTE = 'B';
  public static final char JVM_BYTECODE_CHAR = 'C';
  public static final char JVM_BYTECODE_DOUBLE = 'D';
  public static final char JVM_BYTECODE_FLOAT = 'F';
  public static final char JVM_BYTECODE_INT = 'I';
  public static final char JVM_BYTECODE_LONG = 'J';
  public static final char JVM_BYTECODE_SHORT = 'S';
  public static final char JVM_BYTECODE_ARRAY = '[';
  public static final char JVM_BYTECODE_OBJECT_START = 'L';
  public static final char JVM_BYTECODE_OBJECT_END = ';';
  public static final char JAVA_PACKAGE_SPLIT = '.';
  public static final char JVM_BYTECODE_PACKAGE_SPLIT = '/';
  public static final char METHOD_START = '(';
  public static final Object METHOD_END = ')';

  public static final String JAVA_IDENT_REGEX = "(?:[_$a-zA-Z][_$a-zA-Z0-9]*)";
  public static final String JAVA_NAME_REGEX = "(?:" + JAVA_IDENT_REGEX + "(?:\\." + JAVA_IDENT_REGEX + ")*)";
  public static final String CLASS_DESC = "(?:L" + JAVA_IDENT_REGEX   + "(?:\\/" + JAVA_IDENT_REGEX + ")*;)";
  public static final String ARRAY_DESC  = "(?:\\[+(?:(?:[VZBCDFIJS])|" + CLASS_DESC + "))";
  public static final String DESC_REGEX = "(?:(?:[VZBCDFIJS])|" + CLASS_DESC + "|" + ARRAY_DESC + ")";
  public static final Pattern DESC_PATTERN = Pattern.compile(DESC_REGEX);
  public static final String METHOD_DESC_REGEX = "(?:("+JAVA_IDENT_REGEX+")?\\(("+DESC_REGEX+"*)\\)("+DESC_REGEX+")?)";
  public static final Pattern METHOD_DESC_PATTERN = Pattern.compile(METHOD_DESC_REGEX);
  public static final Pattern GETTER_METHOD_DESC_PATTERN = Pattern.compile("get([A-Z][_a-zA-Z0-9]*)\\(\\)(" + DESC_REGEX + ")");
  public static final Pattern SETTER_METHOD_DESC_PATTERN = Pattern.compile("set([A-Z][_a-zA-Z0-9]*)\\((" + DESC_REGEX + ")\\)V");
  public static final Pattern IS_HAS_CAN_METHOD_DESC_PATTERN = Pattern.compile("(?:is|has|can)([A-Z][_a-zA-Z0-9]*)\\(\\)Z");

  public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];

  static {
    primitive2JVMBytecodeMap.put(Boolean.TYPE, JVM_BYTECODE_BOOLEAN);
    primitive2JVMBytecodeMap.put(Byte.TYPE, JVM_BYTECODE_BYTE);
    primitive2JVMBytecodeMap.put(Character.TYPE, JVM_BYTECODE_CHAR);
    primitive2JVMBytecodeMap.put(Short.TYPE, JVM_BYTECODE_SHORT);
    primitive2JVMBytecodeMap.put(Integer.TYPE, JVM_BYTECODE_INT);
    primitive2JVMBytecodeMap.put(Long.TYPE, JVM_BYTECODE_LONG);
    primitive2JVMBytecodeMap.put(Double.TYPE, JVM_BYTECODE_DOUBLE);
    primitive2JVMBytecodeMap.put(Float.TYPE, JVM_BYTECODE_FLOAT);
    primitive2JVMBytecodeMap.put(Void.TYPE, JVM_BYTECODE_VOID);
  }
  
  /**
   * Get JVM bytecode type for specified class.
   * boolean[].class => "[Z" 
   * Object.class => "Ljava/lang/Object;"
   * 
   * @param clazz
   *          the class
   * @return the bytecode type
   */
  public static String getBytecodeType(Class<?> clazz) {
    Assert.notNull(clazz);
    StringBuilder sb = new StringBuilder(64);

    while (clazz.isArray()) {
      sb.append(JVM_BYTECODE_ARRAY);
      clazz = clazz.getComponentType();
    }

    if (clazz.isPrimitive()) {
      sb.append(primitive2JVMBytecodeMap.get(clazz));
    } else {
      sb.append(JVM_BYTECODE_OBJECT_START);
      sb.append(clazz.getName().replace(JAVA_PACKAGE_SPLIT, JVM_BYTECODE_PACKAGE_SPLIT));
      sb.append(JVM_BYTECODE_OBJECT_END);
    }
    return sb.toString();
  }

  /**
   * Get JVM bytecode type for specified class array.
   * 
   * [int.class, boolean[].class, Object.class] => "I[ZLjava/lang/Object;"
   * 
   * @param classes
   *          the class array
   * @return the bytecode type
   */
  public static String getBytecodeType(final Class<?>[] classes) {
    Assert.notNull(classes);
    if (0 == classes.length) {
      return StringConstants.EMPTY_STRING;
    }

    StringBuilder sb = new StringBuilder(64);
    for (Class<?> clazz : classes) {
      sb.append(getBytecodeType(clazz));
    }
    return sb.toString();
  }

  /**
   * Get JVM bytecode type for specified method.
   * 
   * int do(int arg1) => "do(I)I"
   * void do(String arg1,boolean arg2) => "do(Ljava/lang/String;Z)V"
   * 
   * @param method
   *         the method
   * @return the bytecode type
   */
  public static String getBytecodeType(final Method method) {
    Assert.notNull(method);
    StringBuilder sb = new StringBuilder(method.getName()).append(METHOD_START);
    for (Class<?> parameterType : method.getParameterTypes()) {
      sb.append(getBytecodeType(parameterType));
    }
    sb.append(METHOD_END).append(getBytecodeType(method.getReturnType()));
    return sb.toString();
  }

  /**
   * Get JVM bytecode type for specified constructor.
   * 
   * "()V", "(Ljava/lang/String;I)V"
   * 
   * @param c
   *         the constructor
   * @return the bytecode type
   */
  public static String getBytecodeType(final Constructor<?> c) {
    StringBuilder sb = new StringBuilder(METHOD_START);
    for (Class<?> parameterType : c.getParameterTypes()) {
      sb.append(getBytecodeType(parameterType));
    }
    sb.append(METHOD_END).append(JVM_BYTECODE_VOID);
    return sb.toString();
  }
  
  /**
   * JVM bytecode type to class.
   * "[Z" => boolean[].class
   * "[[Ljava/util/Map;" => java.util.Map[][].class
   * 
   * @param bytecodeType
   *          the JVM bytecode type
   * @return the class
   * @throws ClassNotFoundException 
   */
  public static Class<?> bytecodeType2Class(String bytecodeType) throws ClassNotFoundException {
    return bytecodeType2Class(bytecodeType, ReflectUtil.class.getClassLoader());
  }

  /**
   * JVM bytecode type to class.
   * "[Z" => boolean[].class
   * "[[Ljava/util/Map;" => java.util.Map[][].class
   * 
   * @param jvmBytecodeType
   *          the JVM bytecode type
   * @param cl
   *          the classloader to load the class
   * @return the class
   * @throws ClassNotFoundException 
   */
  public static Class<?> bytecodeType2Class(String jvmBytecodeType, ClassLoader cl) throws ClassNotFoundException {
    Assert.notNull(jvmBytecodeType);
    switch( jvmBytecodeType.charAt(0) ) {
      case JVM_BYTECODE_VOID:
        return void.class;
      case JVM_BYTECODE_BOOLEAN:
        return boolean.class;
      case JVM_BYTECODE_BYTE:
        return byte.class;
      case JVM_BYTECODE_CHAR:
        return char.class;
      case JVM_BYTECODE_DOUBLE:
        return double.class;
      case JVM_BYTECODE_FLOAT:
        return float.class;
      case JVM_BYTECODE_INT:
        return int.class;
      case JVM_BYTECODE_LONG:
        return long.class;
      case JVM_BYTECODE_SHORT:
        return short.class;
      case JVM_BYTECODE_OBJECT_START:
        // "Ljava/lang/Object;" ==> "java.lang.Object"
        jvmBytecodeType = jvmBytecodeType.substring(1, jvmBytecodeType.length() - 1).replace(JVM_BYTECODE_PACKAGE_SPLIT, JAVA_PACKAGE_SPLIT); 
        break;
      case JVM_BYTECODE_ARRAY:
        // "[[Ljava/lang/Object;" ==> "[[Ljava.lang.Object;"
        jvmBytecodeType = jvmBytecodeType.replace(JVM_BYTECODE_PACKAGE_SPLIT, JAVA_PACKAGE_SPLIT);
        break;
      default:
        throw new ClassNotFoundException(String.format("Class not found for [%s]", jvmBytecodeType));
    }

    if (null == cl) {
      cl = ReflectUtil.class.getClassLoader();
    }
    Class<?> clazz = BYTECODE_CLASS_CACHE.get(jvmBytecodeType);
    if (null == clazz) {
        clazz = Class.forName(jvmBytecodeType, true, cl);
        BYTECODE_CLASS_CACHE.put(jvmBytecodeType, clazz);
    }
    return clazz;
  }

  /**
   * JVM bytecode type to class array.
   * 
   * @param jvmBytecodeType
   *          the JVM bytecode type
   * 
   * @return the class array
   * @throws ClassNotFoundException 
   */
  public static Class<?>[] bytecodeType2ClassArray(String jvmBytecodeType) throws ClassNotFoundException {
    return bytecodeType2ClassArray(jvmBytecodeType, ReflectUtil.class.getClassLoader());
  }

  /**
   * JVM bytecode type to class array.
   * 
   * @param jvmBytecodeType
   *          the JVM bytecode type
   * @param cl
   *          the classloader to load the class
   * @return the class array
   * @throws ClassNotFoundException 
   */
  public static Class<?>[] bytecodeType2ClassArray(String jvmBytecodeType, ClassLoader cl) throws ClassNotFoundException {
    Assert.notNull(jvmBytecodeType);
    if (0 == jvmBytecodeType.length()) {
      return EMPTY_CLASS_ARRAY;
    }

    List<Class<?>> classes = new ArrayList<Class<?>>();
    Matcher m = DESC_PATTERN.matcher(jvmBytecodeType);
    while (m.find()) {
      classes.add(bytecodeType2Class(m.group(), cl));
    }
    return classes.toArray(EMPTY_CLASS_ARRAY);
  }
}
