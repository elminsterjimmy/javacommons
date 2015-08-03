package com.elminster.common.util.test;

import org.junit.Assert;
import org.junit.Test;

import com.elminster.common.util.ObjectUtil;

public class ObjectUtilTest {

  @Test
  public void testIsEqual() {
    Assert.assertTrue(ObjectUtil.isEqual(1, 1));
    Assert.assertTrue(ObjectUtil.isEqual((byte) 1, (byte) 1));
  }

  @Test
  public void testToString() {
    String s = "abc";
    System.out.print(ObjectUtil.buildToStringByReflect(s));
  }
  
  @Test
  public void testToObjectArray() {
    String[] s1 = { "1", "2", "3" };
    Assert.assertTrue(ObjectUtil.isEqual(ObjectUtil.toObjectArray(s1), s1));
    String s = "1";
    Assert.assertTrue(ObjectUtil.isEqual(ObjectUtil.toObjectArray(s), new String[] {"1"}));
  }
}
