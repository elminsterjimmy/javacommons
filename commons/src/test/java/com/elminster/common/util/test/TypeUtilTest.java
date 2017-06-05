package com.elminster.common.util.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.elminster.common.util.ReflectUtil;
import com.elminster.common.util.TypeUtil;
import com.elminster.common.util.TypeUtil.CompactedType;
import com.elminster.common.util.TypeUtil.ClassTypeDef;

public class TypeUtilTest {

  @Test
  public void testGetClassGenericTypesClasses() {
    Class<?> clazz = CT3.class;
    ClassTypeDef[] classTypeDef = TypeUtil.getClassGenericTypesClass(clazz);
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
    Class<?> clazz = GT.class;
    Field fieldclist = ReflectUtil.getDeclaredField(clazz, "clist");
    Field fieldtriMap = ReflectUtil.getDeclaredField(clazz, "triMap");
    CompactedType ctclist = TypeUtil.getFieldGenericTypeClass(clazz, fieldclist);
    CompactedType cttriMap = TypeUtil.getFieldGenericTypeClass(clazz, fieldtriMap);
    
    clazz = CT3.class;
    Field fieldT = ReflectUtil.getDeclaredField(clazz, "t");
    Field fieldK = ReflectUtil.getDeclaredField(clazz, "k");
    Field fieldLK = ReflectUtil.getDeclaredField(clazz, "lk");
    Field fieldLIT = ReflectUtil.getDeclaredField(clazz, "lit");
    Field fieldLS = ReflectUtil.getDeclaredField(clazz, "ls");
    CompactedType ctT = TypeUtil.getFieldGenericTypeClass(clazz, fieldT);
    CompactedType ctK = TypeUtil.getFieldGenericTypeClass(clazz, fieldK);
    CompactedType ctLK = TypeUtil.getFieldGenericTypeClass(clazz, fieldLK);
    CompactedType ctLIT = TypeUtil.getFieldGenericTypeClass(clazz, fieldLIT);
    CompactedType ctLS = TypeUtil.getFieldGenericTypeClass(clazz, fieldLS);
    // List<List<Map<String, Map<String, String>>>>
    Assert.assertEquals("java.util.List<java.util.List<java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>>>>", ctclist.toString());
    Assert.assertEquals("List<List<Map<String, Map<String, String>>>>", ctclist.toString(true));
    // Map<String, Map<String, Map<String, String>>>
    Assert.assertEquals("java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>>>", cttriMap.toString());
    Assert.assertEquals("Map<String, Map<String, Map<String, String>>>", cttriMap.toString(true));
    // Long
    Assert.assertEquals("java.lang.Long", ctT.toString());
    Assert.assertEquals("Long", ctT.toString(true));
    // K -> List<Short>
    Assert.assertEquals("java.util.List<java.lang.Short>", ctK.toString());
    Assert.assertEquals("List<Short>", ctK.toString(true));
    // List<K> -> List<List<Short>>
    Assert.assertEquals("java.util.List<java.util.List<java.lang.Short>>", ctLK.toString());
    Assert.assertEquals("List<List<Short>>", ctLK.toString(true));
    // List<IT1<String>>
    Assert.assertEquals("java.util.List<com.elminster.common.util.test.TypeUtilTest$IT1<java.lang.String>>", ctLIT.toString());
    Assert.assertEquals("List<IT1<String>>", ctLIT.toString(true));
    // List<String>
    Assert.assertEquals("java.util.List<java.lang.String>", ctLS.toString());
    Assert.assertEquals("List<String>", ctLS.toString(true));
  }
  
  @Test
  public void testGetMethodReturnTypeClass() {
    Class<?> clazz = CT3.class;
    Method methodString = ReflectUtil.getDeclaredMethod(clazz, "getString", String.class);
    Method methodIT1 = ReflectUtil.getDeclaredMethod(clazz, "getIT1", String.class);
    Method methodIT2 = ReflectUtil.getDeclaredMethod(clazz, "getIT2", List.class, Integer.class);
    Method methodCT1 = ReflectUtil.getDeclaredMethod(clazz, "getCT1", Long.class);
    Method methodCT2 = ReflectUtil.getDeclaredMethod(clazz, "getCT2", String.class, List.class);
    Assert.assertEquals(String.class.getName(), TypeUtil.getMethodReturnTypeClass(clazz, methodString).toString());
    Assert.assertEquals(Object.class.getName(), TypeUtil.getMethodReturnTypeClass(clazz, methodIT1).toString());
    Assert.assertEquals("java.util.List<java.lang.Integer>", TypeUtil.getMethodReturnTypeClass(clazz, methodIT2).toString());
    Assert.assertEquals(Long.class.getName(), TypeUtil.getMethodReturnTypeClass(clazz, methodCT1).toString());
    Assert.assertEquals("java.util.List<java.lang.Short>", TypeUtil.getMethodReturnTypeClass(clazz, methodCT2).toString());
  }
  
  @Test
  public void testgetMethodParameterTypeClass() {
    Class<?> clazz = CT3.class;
    Method methodString = ReflectUtil.getDeclaredMethod(clazz, "getString", String.class);
    Method methodIT1 = ReflectUtil.getDeclaredMethod(clazz, "getIT1", String.class);
    Method methodIT2 = ReflectUtil.getDeclaredMethod(clazz, "getIT2", List.class, Integer.class);
    Method methodCT1 = ReflectUtil.getDeclaredMethod(clazz, "getCT1", Long.class);
    Method methodCT2 = ReflectUtil.getDeclaredMethod(clazz, "getCT2", String.class, List.class);
    Assert.assertEquals(String.class.getName(), TypeUtil.getMethodParameterTypeClass(clazz, methodString, 0).toString());
    Assert.assertEquals(Object.class.getName(), TypeUtil.getMethodParameterTypeClass(clazz, methodIT1, 0).toString());
    Assert.assertEquals("java.util.List<java.lang.Integer>", TypeUtil.getMethodParameterTypeClass(clazz, methodIT2, 0).toString());
    Assert.assertEquals(Long.class.getName(), TypeUtil.getMethodParameterTypeClass(clazz, methodCT1, 0).toString());
    Assert.assertEquals("java.util.List<java.lang.Short>", TypeUtil.getMethodParameterTypeClass(clazz, methodCT2, 1).toString());
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
  
  class GT {
    List<List<Map<String, Map<String, String>>>> clist;
    Map<String, Map<String, Map<String, String>>> triMap;
  }
}
