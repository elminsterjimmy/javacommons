package com.elminster.common.util.test;

import org.junit.Assert;
import org.junit.Test;

import com.elminster.common.util.ArrayUtil;
import com.elminster.common.util.ObjectUtil;

public class ArrayUtilTest {

  @Test
  public void testResizeArray() {
    String[] s1 = { "1", "2", "3", "4", null, null };
    String[] s2 = { "1", "2", "3", "4" };
    String[] s3 = { "1", "2", "3", "4", null };
    String[] s4 = { };
    Assert.assertTrue(ObjectUtil.isEqual(ArrayUtil.resize(s1, 4), s2));
    Assert.assertTrue(ObjectUtil.isEqual(ArrayUtil.resize(s1, 5), s3));
    Assert.assertTrue(ObjectUtil.isEqual(ArrayUtil.resize(s1, 0), s4));
  }
  
  @Test
  public void testJoinArray() {
    String[] s1 = { "1", "2", "3" };
    String[] s2 = { "4", "5", "6" };
    String[] s3 = { "1", "2", "3", "4", "5", "6" };
    Assert.assertTrue(ObjectUtil.isEqual(ArrayUtil.joinArray(s1, s2), s3));
  }
  
  @Test
  public void testRevertArray() {
    String[] s1 = { "1", "2", "3", "4" };
    String[] s2 = { "4", "3", "2", "1" };
    Assert.assertTrue(ObjectUtil.isEqual(ArrayUtil.revertArray(s1), s2));
    String[] s3 = { "1", "2", "3", "4", "5" };
    String[] s4 = { "5", "4", "3", "2", "1" };
    Assert.assertTrue(ObjectUtil.isEqual(ArrayUtil.revertArray(s3), s4));
  }
  
  @Test
  public void testJoinString() {
    String[] s1 = { "1", "2", "3", "4" };
    String s2 = "1,2,3,4";
    String s3 = "1, 2, 3, 4";
    Assert.assertTrue(ObjectUtil.isEqual(ArrayUtil.joinString(s1), s2));
    Assert.assertTrue(ObjectUtil.isEqual(ArrayUtil.joinString(s1, ", "), s3));
  }
}
