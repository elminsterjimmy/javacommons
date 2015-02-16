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
  public void testJoinArray() {
    String[] s1 = { "1", "2", "3" };
    String[] s2 = { "4", "5", "6" };
    String[] s3 = { "1", "2", "3", "4", "5", "6" };
    Assert.assertTrue(ObjectUtil.isEqual(ObjectUtil.joinArray(s1, s2), s3));
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
