package com.elminster.common.util.test;

import org.junit.Assert;
import org.junit.Test;

import com.elminster.common.util.BytecodeUtil;

public class BytecodeUtilTest {

  @Test
  public void testGetJVMBytecodeType() {
    Assert.assertEquals("Z", BytecodeUtil.getBytecodeType(boolean.class));
    Assert.assertEquals("[[[I", BytecodeUtil.getBytecodeType(int[][][].class));
    Assert.assertEquals("[[Ljava/lang/Object;", BytecodeUtil.getBytecodeType(Object[][].class));
    Assert.assertEquals("I[ZLjava/lang/Object;", BytecodeUtil.getBytecodeType(new Class<?>[] { int.class, boolean[].class, Object.class }));
  }

  @Test
  public void testBytecodeType2Class() throws Exception {
    Assert.assertEquals(boolean.class, BytecodeUtil.bytecodeType2Class("Z"));
    Assert.assertEquals(boolean[].class, BytecodeUtil.bytecodeType2Class("[Z"));
    Assert.assertEquals(int[][].class, BytecodeUtil.bytecodeType2Class(BytecodeUtil.getBytecodeType(int[][].class)));
    Assert.assertEquals(BytecodeUtilTest[].class, BytecodeUtil.bytecodeType2Class(BytecodeUtil.getBytecodeType(BytecodeUtilTest[].class)));

    String bytecodeType;
    Class<?>[] clazz;

    clazz = new Class<?>[] { int.class, getClass(), String.class, int[][].class, boolean[].class };
    bytecodeType = BytecodeUtil.getBytecodeType(clazz);
    Assert.assertArrayEquals(clazz, BytecodeUtil.bytecodeType2ClassArray(bytecodeType));

    clazz = new Class<?>[] {};
    bytecodeType = BytecodeUtil.getBytecodeType(clazz);
    Assert.assertArrayEquals(clazz, BytecodeUtil.bytecodeType2ClassArray(bytecodeType));

    clazz = new Class<?>[] { void.class, String[].class, int[][].class, BytecodeUtilTest[][].class };
    bytecodeType = BytecodeUtil.getBytecodeType(clazz);
    Assert.assertArrayEquals(clazz, BytecodeUtil.bytecodeType2ClassArray(bytecodeType));
  }

}
