package com.elminster.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeUtil {

  public static final TypeUtil INSTANCE = new TypeUtil();

  private final Map<Class<?>, ClassTypeDef[]> classCache = new HashMap<Class<?>, ClassTypeDef[]>();

  private TypeUtil() {
  }

  public void clearCache() {
    classCache.clear();
  }
  
  /**
   * Get the method generic parameter type class.
   * @param clazz the class of the method
   * @param method the method
   * @param idx the parameter index
   * @return the method generic parameter type class
   */
  public List<Class<?>> getMethodParameterTypeClass(Class<?> clazz, Method method, int idx) {
    ClassTypeDef[] defs = classCache.get(clazz);
    if (null == defs) {
      defs = getClassGenericTypesClass(clazz);
    }
    
    List<Class<?>> actualClass = null;
    Type type = method.getGenericParameterTypes()[idx];
    if (type instanceof Class<?>) {
      actualClass = new ArrayList<Class<?>>(0);
      actualClass.add((Class<?>) type);
    } else if (type instanceof TypeVariable) {
      TypeVariable<?> tv = (TypeVariable<?>) type;
      actualClass = getActualClassFromTypeVariable(tv, defs);
      while (null == actualClass && null != clazz.getSuperclass()) {
        clazz = clazz.getSuperclass();
        actualClass = getMethodReturnTypeClass(clazz, method);
      }
    } else if (type instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) type;
      actualClass = getTypeFromParameterizedType(pt, defs);
    }
    return actualClass;
  }

  /**
   * Get the method generic return type class.
   * @param clazz the class of the method
   * @param method the method
   * @return the method generic return type class
   */
  public List<Class<?>> getMethodReturnTypeClass(Class<?> clazz, Method method) {
    ClassTypeDef[] defs = classCache.get(clazz);
    if (null == defs) {
      defs = getClassGenericTypesClass(clazz);
    }

    List<Class<?>> actualClass = null;
    Type type = method.getGenericReturnType();
    if (type instanceof Class<?>) {
      actualClass = new ArrayList<Class<?>>(0);
      actualClass.add((Class<?>) type);
    } else if (type instanceof TypeVariable) {
      TypeVariable<?> tv = (TypeVariable<?>) type;
      actualClass = getActualClassFromTypeVariable(tv, defs);
      while (null == actualClass && null != clazz.getSuperclass()) {
        clazz = clazz.getSuperclass();
        actualClass = getMethodReturnTypeClass(clazz, method);
      }
    } else if (type instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) type;
      actualClass = getTypeFromParameterizedType(pt, defs);
    }
    return actualClass;
  }

  /**
   * Get the field generic type class.
   * @param clazz the class of the field
   * @param field the field
   * @return the actual class of the generic type
   */
  public List<Class<?>> getFieldGenericTypeClass(Class<?> clazz, Field field) {
    ClassTypeDef[] defs = classCache.get(clazz);
    if (null == defs) {
      defs = getClassGenericTypesClass(clazz);
    }

    List<Class<?>> actualClass = null;
    Type type = field.getGenericType();
    if (type instanceof Class<?>) {
      actualClass = new ArrayList<Class<?>>(0);
      actualClass.add((Class<?>) type);
    } else if (type instanceof TypeVariable) {
      TypeVariable<?> tv = (TypeVariable<?>) type;
      actualClass = getActualClassFromTypeVariable(tv, defs);
      while (null == actualClass && null != clazz.getSuperclass()) {
        clazz = clazz.getSuperclass();
        actualClass = getFieldGenericTypeClass(clazz, field);
      }
    } else if (type instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) type;
      actualClass = getTypeFromParameterizedType(pt, defs);
    }
    return actualClass;
  }

  private List<Class<?>> getActualClassFromTypeVariable(TypeVariable<?> tv, ClassTypeDef[] defs) {
    if (null != defs) {
      for (ClassTypeDef def : defs) {
        if (tv.getName().equals(def.getTypeVariableName()) && tv.getGenericDeclaration() == def.getTypeDefClass()) {
          return def.getActualClass();
        }
      }
    }
    return null;
  }

  private List<Class<?>> getTypeFromParameterizedType(ParameterizedType pt, ClassTypeDef[] defs) {
    List<Class<?>> rtn = null;
    Type rawType = pt.getRawType();
    if (rawType instanceof Class<?>) {
      rtn = new ArrayList<Class<?>>();
      Type[] types = pt.getActualTypeArguments();
      for (Type type :types) {
        if (type instanceof TypeVariable<?>) {
          rtn.addAll(getActualClassFromTypeVariable((TypeVariable<?>)type, defs));
        } else if (type instanceof ParameterizedType) {
          rtn.add((Class<?>) rawType);
          rtn.addAll(getTypeFromParameterizedType((ParameterizedType) type, defs));
        } else if (type instanceof Class<?>) {
          rtn.add((Class<?>) rawType);
          rtn.add((Class<?>) type);
        }
      }
      
    }
    return rtn;
  }

  /**
   * Get the generic types actual classes from specified class.
   * 
   * @param clazz
   *          the class
   * @return the generic types actual classes
   */
  public ClassTypeDef[] getClassGenericTypesClass(Class<?> clazz) {
    Type genericSuperclass = clazz.getGenericSuperclass();
    ClassTypeDef[] superClassGenericClass = null;
    ClassTypeDef[] interfaceGenericClass = null;
    if (null != genericSuperclass) {
      superClassGenericClass = getClassGenericDef(genericSuperclass);
    }

    Type[] genericInterfaces = clazz.getGenericInterfaces();
    if (null != genericInterfaces) {
      for (Type genericInterface : genericInterfaces) {
        ClassTypeDef[] ifgc = getClassGenericDef(genericInterface);
        interfaceGenericClass = (ClassTypeDef[]) ObjectUtil.joinArray(interfaceGenericClass, ifgc);
      }
    }
    ClassTypeDef[] classTypeClass = (ClassTypeDef[]) ObjectUtil
        .joinArray(superClassGenericClass, interfaceGenericClass);
    classCache.put(clazz, classTypeClass);
    return classTypeClass;
  }

  private ClassTypeDef[] getClassGenericDef(Type type) {
    ClassTypeDef[] classTypeDef = null;
    if (type instanceof ParameterizedType) {
      classTypeDef = getClassTypeDefFromParameterizedType((ParameterizedType) type);
    }
    return classTypeDef;
  }

  /**
   * 
   * @param parameterizedType
   * @return
   */
  private ClassTypeDef[] getClassTypeDefFromParameterizedType(ParameterizedType parameterizedType) {
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
          classTypeDefs[i++] = ctd;
        }
      }
    }
    return classTypeDefs;
  }

  /**
   * 
   * @param type
   * @return
   */
  private List<Class<?>> getClassFromRawType(Type type) {
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

  public class ClassTypeDef {
    Class<?> typeDefClass;
    String typeVariableName;
    // the actual class also can be a ParameterizedType
    List<Class<?>> actualClass;

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
