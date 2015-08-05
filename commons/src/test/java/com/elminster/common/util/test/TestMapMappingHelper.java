package com.elminster.common.util.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.elminster.common.util.MapMappingHelper;

public class TestMapMappingHelper {

  @Test
  public void testMappingMapToTest1NoCast() {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("strKey", "string");
    map.put("intKey", 10);
    map.put("boolKey", true);
    map.put("longKey", 100L);
    byte b = 5;
    map.put("byteKey", b);
    short st = 15;
    map.put("shortKey", st);
    map.put("floatKey", 1.5f);
    map.put("doubleKey", 15.4d);
    Class1 test1 = new Class1();
    MapMappingHelper.mappingMapToObject(map, test1);
    Class1 expected = new Class1();
    expected.setBoolKey(true);
    expected.setByteKey(b);
    expected.setDoubleKey(15.4d);
    expected.setFloatKey(1.5f);
    expected.setIntKey(10);
    expected.setLongKey(100L);
    expected.setShortKey(st);
    expected.setStrKey("string");
    Assert.assertEquals(expected, test1);
  }
  
  @Test
  public void testMappingMapToTest1Cast() {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("strKey", "string");
    map.put("intKey", 10L);
    map.put("boolKey", "true");
    map.put("longKey", 100);
    byte b = 5;
    map.put("byteKey", 5);
    short st = 15;
    map.put("shortKey", 15);
    map.put("floatKey", 1.5);
    map.put("doubleKey", 15.4);
    Class1 test1 = new Class1();
    MapMappingHelper.mappingMapToObject(map, test1);
    Class1 expected = new Class1();
    expected.setBoolKey(true);
    expected.setByteKey(b);
    expected.setDoubleKey(15.4d);
    expected.setFloatKey(1.5f);
    expected.setIntKey(10);
    expected.setLongKey(100L);
    expected.setShortKey(st);
    expected.setStrKey("string");
    Assert.assertEquals(expected, test1);
  }
  
  @Test
  public void testMappingMapToTest2Map() {
    Map<String, Object> sbMap = new HashMap<String, Object>();
    sbMap.put("strKey", "string");
    sbMap.put("intKey", 10);
    sbMap.put("boolKey", true);
    sbMap.put("longKey", 100L);
    byte b = 5;
    sbMap.put("byteKey", b);
    short st = 15;
    sbMap.put("shortKey", st);
    sbMap.put("floatKey", 1.5f);
    sbMap.put("doubleKey", 15.4d);
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("test1", sbMap);
    Class2 t2 = new Class2();
    MapMappingHelper.mappingMapToObject(map, t2);
    Class2 expected = new Class2();
    Class1 t1 = new Class1();
    t1.setBoolKey(true);
    t1.setByteKey(b);
    t1.setDoubleKey(15.4d);
    t1.setFloatKey(1.5f);
    t1.setIntKey(10);
    t1.setLongKey(100L);
    t1.setShortKey(st);
    t1.setStrKey("string");
    expected.setTest1(t1);
    Assert.assertEquals(expected, t2);
  }
  
  @Test
  public void testMappingMapToTest2Object() {
    Map<String, Object> map = new HashMap<String, Object>();
    byte b = 5;
    short st = 15;
    Class1 t1 = new Class1();
    t1.setBoolKey(true);
    t1.setByteKey(b);
    t1.setDoubleKey(15.4d);
    t1.setFloatKey(1.5f);
    t1.setIntKey(10);
    t1.setLongKey(100L);
    t1.setShortKey(st);
    t1.setStrKey("string");
    map.put("test1", t1);
    Class2 t2 = new Class2();
    MapMappingHelper.mappingMapToObject(map, t2);
    Class2 expected = new Class2();
    expected.setTest1(t1);
    Assert.assertEquals(expected, t2);
  }
}
