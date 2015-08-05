package com.elminster.common.util.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.elminster.common.util.ReflectUtil;
import com.elminster.common.util.TypeUtil;
import com.elminster.common.util.TypeUtil.ClassTypeDef;

public class TypeUtilTest {

  @Test
  public void testGetClassGenericTypesClasses() {
    Class<?> clazz = CT3.class;
    ClassTypeDef[] classTypeDef = TypeUtil.INSTANCE.getClassGenericTypesClass(clazz);
    Assert.assertTrue(3 == classTypeDef.length);
    Assert.assertTrue(Short.class == classTypeDef[0].getActualClass().get(1));
    Assert.assertTrue(CT2.class == classTypeDef[0].getTypeDefClass());
    Assert.assertTrue("K".equals(classTypeDef[0].getTypeVariableName()));
    Assert.assertTrue(String.class == classTypeDef[1].getActualClass().get(0));
    Assert.assertTrue(IT1.class == classTypeDef[1].getTypeDefClass());
    Assert.assertTrue("E".equals(classTypeDef[1].getTypeVariableName()));
    Assert.assertTrue(Integer.class == classTypeDef[2].getActualClass().get(0));
    Assert.assertTrue(IT2.class == classTypeDef[2].getTypeDefClass());
    Assert.assertTrue("E".equals(classTypeDef[2].getTypeVariableName()));
  }
  
  @Test
  public void testgetFieldTypeClass() {
    Class<?> clazz = CT3.class;
    Field fieldT = ReflectUtil.getDeclaredField(clazz, "t");
    Field fieldK = ReflectUtil.getDeclaredField(clazz, "k");
    Field fieldLK = ReflectUtil.getDeclaredField(clazz, "lk");
    Field fieldLIT = ReflectUtil.getDeclaredField(clazz, "lit");
    Field fieldLS = ReflectUtil.getDeclaredField(clazz, "ls");
    Assert.assertTrue(Long.class == TypeUtil.INSTANCE.getFieldGenericTypeClass(clazz, fieldT).get(0));
    Assert.assertTrue(List.class == TypeUtil.INSTANCE.getFieldGenericTypeClass(clazz, fieldK).get(0));
    List<Class<?>> TypeClassLK = TypeUtil.INSTANCE.getFieldGenericTypeClass(clazz, fieldLK);
    Assert.assertTrue(List.class == TypeClassLK.get(0));
    Assert.assertTrue(Short.class == TypeClassLK.get(1));
    List<Class<?>> TypeClassLIT = TypeUtil.INSTANCE.getFieldGenericTypeClass(clazz, fieldLIT);
    Assert.assertTrue(List.class == TypeClassLIT.get(0));
    Assert.assertTrue(IT1.class == TypeClassLIT.get(1));
    Assert.assertTrue(String.class == TypeClassLIT.get(2));
    List<Class<?>> TypeClassLS = TypeUtil.INSTANCE.getFieldGenericTypeClass(clazz, fieldLS);
    Assert.assertTrue(List.class == TypeClassLS.get(0));
    Assert.assertTrue(String.class == TypeClassLS.get(1));
  }
  
  @Ignore
  @Test
  public void testGetMethodReturnTypeClass() {
    Class<?> clazz = CT3.class;
    Method methodString = ReflectUtil.getDeclaredMethod(clazz, "getString", String.class);
    Method methodIT1 = ReflectUtil.getDeclaredMethod(clazz, "getIT1", String.class);
    Method methodIT2 = ReflectUtil.getDeclaredMethod(clazz, "getIT2", List.class, Integer.class);
    Method methodCT1 = ReflectUtil.getDeclaredMethod(clazz, "getCT1", Long.class);
    Method methodCT2 = ReflectUtil.getDeclaredMethod(clazz, "getCT2", String.class, List.class);
    Assert.assertTrue(String.class == TypeUtil.INSTANCE.getMethodReturnTypeClass(clazz, methodString).get(0));
    Assert.assertTrue(Object.class == TypeUtil.INSTANCE.getMethodReturnTypeClass(clazz, methodIT1).get(0));
    Assert.assertTrue(List.class == TypeUtil.INSTANCE.getMethodReturnTypeClass(clazz, methodIT2).get(0));
    Assert.assertTrue(Integer.class == TypeUtil.INSTANCE.getMethodReturnTypeClass(clazz, methodIT2).get(1));
    Assert.assertTrue(Long.class == TypeUtil.INSTANCE.getMethodReturnTypeClass(clazz, methodCT1).get(0));
    Assert.assertTrue(List.class == TypeUtil.INSTANCE.getMethodReturnTypeClass(clazz, methodCT2).get(0));
    Assert.assertTrue(Short.class == TypeUtil.INSTANCE.getMethodReturnTypeClass(clazz, methodCT2).get(1));
  }
  
  @Ignore
  @Test
  public void testgetMethodParameterTypeClass() {
    Class<?> clazz = CT3.class;
    Method methodString = ReflectUtil.getDeclaredMethod(clazz, "getString", String.class);
    Method methodIT1 = ReflectUtil.getDeclaredMethod(clazz, "getIT1", String.class);
    Method methodIT2 = ReflectUtil.getDeclaredMethod(clazz, "getIT2", List.class, Integer.class);
    Method methodCT1 = ReflectUtil.getDeclaredMethod(clazz, "getCT1", Long.class);
    Method methodCT2 = ReflectUtil.getDeclaredMethod(clazz, "getCT2", String.class, List.class);
    Assert.assertTrue(String.class == TypeUtil.INSTANCE.getMethodParameterTypeClass(clazz, methodString, 0).get(0));
    Assert.assertTrue(Object.class == TypeUtil.INSTANCE.getMethodParameterTypeClass(clazz, methodIT1, 0).get(0));
    Assert.assertTrue(List.class == TypeUtil.INSTANCE.getMethodParameterTypeClass(clazz, methodIT2, 0).get(0));
    Assert.assertTrue(Integer.class == TypeUtil.INSTANCE.getMethodParameterTypeClass(clazz, methodIT2, 0).get(1));
    Assert.assertTrue(Long.class == TypeUtil.INSTANCE.getMethodParameterTypeClass(clazz, methodCT1, 0).get(0));
    Assert.assertTrue(List.class == TypeUtil.INSTANCE.getMethodParameterTypeClass(clazz, methodCT2, 1).get(0));
    Assert.assertTrue(Short.class == TypeUtil.INSTANCE.getMethodParameterTypeClass(clazz, methodCT2, 1).get(1));
  }
  
  interface IT1<E> {
    E getIT1(E e);
  }
  
  interface IT2<E> {
    List<E> getIT2(List<E> e, E e1);
  }
  
  interface IT3<E, K> extends IT1<E> {
    Map<K, E> getMappingIT3();
  }
  
  interface ITE extends IT3<String, Integer> {
    
  }
  
  class CT1<T> {
    public T getCT1(T t) {
      return null;
    }
  }
  
  class CT2<K> extends CT1<Long> {
    Long t;
    K k;
    List<K> lk;
    List<IT1<String>> lit;
    List<String> ls;
    public K getCT2(String s, K k) {
      return null;
    }
  }
  
  class CT3 extends CT2<List<Short>> implements IT1<String>, IT2<Integer> {

    @Override
    public List<Integer> getIT2(List<Integer> e, Integer e1) {
      return null;
    }

    @Override
    public String getIT1(String s) {
      return null;
    }
    
    public String getString(String s) {
      return null;
    }
  }
}
