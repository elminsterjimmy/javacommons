package com.elminster.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;

import com.elminster.common.constants.Constants.StringConstants;

/**
 * Type Utilities.
 * 
 * @author jinggu
 * @version 1.0
 */
abstract public class TypeUtil {

  /** cache the class type information. */
  private final static Map<Class<?>, ClassTypeDef[]> classCache = new ConcurrentHashMap<>();

  public static void clearCache() {
    classCache.clear();
  }

  /**
   * Get the method generic parameter type class.
   * 
   * @param clazz
   *          the class of the method
   * @param method
   *          the method
   * @param idx
   *          the parameter index
   * @return the method generic parameter type class
   */
  public static CompactedType getMethodParameterTypeClass(Class<?> clazz, Method method, int idx) {
    CompactedType ct = null;
    Type type = method.getGenericParameterTypes()[idx];
    if (type instanceof Class<?>) {
      ct = new CompactedType();
      ct.type = (Class<?>) type;
      return ct;
    } else if (type instanceof TypeVariable) {
      TypeVariable<?> tv = (TypeVariable<?>) type;
      ct = getActualClassFromTypeVariableReadable(tv, clazz);
      while (null == ct && null != clazz.getSuperclass()) {
        clazz = clazz.getSuperclass();
        ct = getMethodParameterTypeClass(clazz, method, idx);
      }
    } else if (type instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) type;
      ct = getTypeFromParameterizedType(pt, clazz);
    }
    return ct;
  }

  /**
   * Get the method generic return type class.
   * 
   * @param clazz
   *          the class of the method
   * @param method
   *          the method
   * @return the method generic return type class
   */
  public static CompactedType getMethodReturnTypeClass(Class<?> clazz, Method method) {
    CompactedType ct = null;
    Type type = method.getGenericReturnType();
    if (type instanceof Class<?>) {
      ct = new CompactedType();
      ct.type = (Class<?>) type;
      return ct;
    } else if (type instanceof TypeVariable) {
      TypeVariable<?> tv = (TypeVariable<?>) type;
      ct = getActualClassFromTypeVariableReadable(tv, clazz);
      while (null == ct && null != clazz.getSuperclass()) {
        clazz = clazz.getSuperclass();
        ct = getMethodReturnTypeClass(clazz, method);
      }
    } else if (type instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) type;
      ct = getTypeFromParameterizedType(pt, clazz);
    }
    return ct;
  }

  private static CompactedType getActualClassFromTypeVariableReadable(TypeVariable<?> tv, Class<?> clazz) {
    ClassTypeDef[] defs = TypeUtil.classCache.get(clazz);
    if (null == defs) {
      defs = getClassGenericTypesClass(clazz);
    }
    if (null != defs) {
      for (ClassTypeDef def : defs) {
        if (tv.getName().equals(def.getTypeVariableName()) && tv.getGenericDeclaration() == def.getTypeDefClass()) {
          return def.ct;
        }
      }
    }
    return null;
  }

  /**
   * Get the generic types actual classes from specified class.
   * 
   * @param clazz
   *          the class
   * @return the generic types actual classes
   */
  public static ClassTypeDef[] getClassGenericTypesClass(Class<?> clazz) {
    Type genericSuperclass = clazz.getGenericSuperclass();
    ClassTypeDef[] superClassGenericClass = null;
    ClassTypeDef[] interfaceGenericClass = null;
    if (null != genericSuperclass) {
      superClassGenericClass = getClassGenericDef(genericSuperclass, clazz);
    }

    Type[] genericInterfaces = clazz.getGenericInterfaces();
    if (null != genericInterfaces) {
      for (Type genericInterface : genericInterfaces) {
        ClassTypeDef[] ifgc = getClassGenericDef(genericInterface, clazz);
        interfaceGenericClass = (ClassTypeDef[]) ArrayUtil.joinArray(interfaceGenericClass, ifgc);
      }
    }
    ClassTypeDef[] classTypeClass = (ClassTypeDef[]) ArrayUtil.joinArray(superClassGenericClass, interfaceGenericClass);
    classCache.put(clazz, classTypeClass);
    return classTypeClass;
  }

  private static ClassTypeDef[] getClassGenericDef(Type type, Class<?> clazz) {
    ClassTypeDef[] classTypeDef = null;
    if (type instanceof ParameterizedType) {
      classTypeDef = getClassTypeDefFromParameterizedType((ParameterizedType) type, clazz);
    }
    return classTypeDef;
  }

  /**
   * 
   * @param parameterizedType
   * @return
   */
  private static ClassTypeDef[] getClassTypeDefFromParameterizedType(ParameterizedType parameterizedType, Class<?> clazz) {
    ClassTypeDef[] classTypeDefs = null;
    Type rawType = parameterizedType.getRawType();
    if (rawType instanceof Class<?>) {
      Class<?> c = (Class<?>) rawType;
      TypeVariable<?>[] tv = c.getTypeParameters();
      classTypeDefs = new ClassTypeDef[tv.length];
      Type[] types = parameterizedType.getActualTypeArguments();
      if (null != types) {
        int i = 0;
        for (Type type : types) {
          ClassTypeDef ctd = new ClassTypeDef();
          ctd.setTypeDefClass(c);
          ctd.setTypeVariableName(tv[i].getName());
          ctd.setActualClass(getClassFromRawType(type));
          ctd.ct = getCompactedTypeFromRawType(type, clazz);
          classTypeDefs[i++] = ctd;
        }
      }
    }
    return classTypeDefs;
  }

  private static List<Class<?>> getClassFromRawType(Type type) {
    List<Class<?>> list = null;
    if (type instanceof Class<?>) {
      list = new ArrayList<Class<?>>(0);
      list.add((Class<?>) type);
      return list;
    } else if (type instanceof ParameterizedType) {
      list = new ArrayList<Class<?>>();
      ParameterizedType pt = (ParameterizedType) type;
      list.addAll(getClassFromRawType(pt.getRawType()));
      Type[] types = pt.getActualTypeArguments();
      for (Type t : types) {
        list.addAll(getClassFromRawType(t));
      }
    }
    return list;
  }

  private static CompactedType getCompactedTypeFromRawType(Type type, Class<?> clazz) {
    CompactedType rtn = null;
    if (type instanceof Class<?>) {
      rtn = new CompactedType();
      rtn.type = (Class<?>) type;
    } else if (type instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) type;
      Type rawType = pt.getRawType();
      if (rawType instanceof Class<?>) {
        Type[] types = pt.getActualTypeArguments();
        rtn = new CompactedType();
        rtn.type = (Class<?>) rawType;
        for (Type t : types) {
          if (t instanceof TypeVariable<?>) {
            rtn.children.add(getActualClassFromTypeVariableReadable((TypeVariable<?>) type, clazz));
          } else if (t instanceof ParameterizedType) {
            rtn.children.add(getCompactedTypeFromRawType((ParameterizedType) t, clazz));
          } else if (t instanceof Class<?>) {
            CompactedType ct = new CompactedType();
            ct.type = (Class<?>) t;
            rtn.children.add(ct);
          }
        }
      } else {
        // TODO
        throw new UnsupportedOperationException(String.format("unsupported rawType [%s].", rawType));
      }
    }
    return rtn;
  }

  public static boolean isPrimitive(Class<?> clazz) {
    if (clazz.isArray()) {
      return isPrimitive(clazz.getComponentType());
    }
    return clazz.isPrimitive();
  }

  /**
   * Get the field generic type class.
   * 
   * @param clazz
   *          the class of the field
   * @param field
   *          the field
   * @return the actual class of the generic type
   */
  public static CompactedType getFieldGenericTypeClass(Class<?> clazz, Field field) {
    CompactedType ct = null;
    Type type = field.getGenericType();
    if (type instanceof Class<?>) {
      ct = new CompactedType();
      ct.type = (Class<?>) type;
      return ct;
    } else if (type instanceof TypeVariable) {
      TypeVariable<?> tv = (TypeVariable<?>) type;
      ct = getActualClassFromTypeVariableReadable(tv, clazz);
      while (null == ct && null != clazz.getSuperclass()) {
        clazz = clazz.getSuperclass();
        ct = getFieldGenericTypeClass(clazz, field);
      }
    } else if (type instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) type;
      ct = getTypeFromParameterizedType(pt, clazz);
    }
    return ct;
  }

  private static CompactedType getTypeFromParameterizedType(ParameterizedType pt, Class<?> clazz) {
    CompactedType rtn = null;
    Type rawType = pt.getRawType();
    if (rawType instanceof Class<?>) {
      rtn = new CompactedType();
      rtn.type = (Class<?>) rawType;
      Type[] types = pt.getActualTypeArguments();
      for (Type type : types) {
        if (type instanceof TypeVariable<?>) {
          rtn.children.add(getActualClassFromTypeVariableReadable((TypeVariable<?>) type, clazz));
        } else if (type instanceof ParameterizedType) {
          rtn.children.add(getTypeFromParameterizedType((ParameterizedType) type, clazz));
        } else if (type instanceof Class<?>) {
          CompactedType ct = new CompactedType();
          ct.type = (Class<?>) type;
          rtn.children.add(ct);
        }
      }

    }
    return rtn;
  }

  public static class CompactedType {
    private static final String CHILD_JOIN_CHAR = StringConstants.COMMA + StringConstants.SPACE;
    private static final String TYPE_START = StringConstants.LESS_THAN;
    private static final String TYPE_END = StringConstants.GREAT_THAN;
    
    Class<?> type;
    List<CompactedType> children = new ArrayList<>();

    public String toString(boolean simple) {
      StringBuilder sb = new StringBuilder();

      sb.append(simple ? type.getSimpleName() : type.getName());
      if (!children.isEmpty()) {
        sb.append(TYPE_START);
        StringJoiner joiner = new StringJoiner(CHILD_JOIN_CHAR);
        
        for (CompactedType child : children) {
          joiner.add(child.toString(simple));
        }
        sb.append(joiner.toString());
        sb.append(TYPE_END);
      }
      return sb.toString();
    }

    public String toString() {
      return toString(false);
    }
  }

  public static class ClassTypeDef {
    Class<?> typeDefClass;
    String typeVariableName;
    // the actual class also can be a ParameterizedType
    List<Class<?>> actualClass;
    CompactedType ct;

    public ClassTypeDef() {

    }

    public Class<?> getTypeDefClass() {
      return typeDefClass;
    }

    public void setTypeDefClass(Class<?> typeDefClass) {
      this.typeDefClass = typeDefClass;
    }

    public String getTypeVariableName() {
      return typeVariableName;
    }

    public void setTypeVariableName(String typeVariableName) {
      this.typeVariableName = typeVariableName;
    }

    public List<Class<?>> getActualClass() {
      return actualClass;
    }

    public void setActualClass(List<Class<?>> actualClass) {
      this.actualClass = actualClass;
    }
  }
}
