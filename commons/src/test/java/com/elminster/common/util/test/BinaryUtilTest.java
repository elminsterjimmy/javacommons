package com.elminster.common.util.test;

import org.junit.Assert;
import org.junit.Test;

import com.elminster.common.util.BinaryUtil;

public class BinaryUtilTest {
  
	@Test
	public void testBinaryUtil() {
	  byte[] b = {-1, 32, -1, -1};
	  int i = BinaryUtil.binary2IntInBigEndian(b, 0, 4);
	  int expectInt = 0xFF20FFFF;
    Assert.assertEquals(expectInt, i);
    Assert.assertEquals("11111111001000001111111111111111", BinaryUtil.getBinary(i));
    Assert.assertEquals("FF20FFFF", BinaryUtil.binary2Hex(BinaryUtil.getBinary(i)));
    long l = BinaryUtil.binary2LongInBigEndian(b, 0, 4);
    long exceptLong = 0xFF20FFFF;
    Assert.assertEquals(exceptLong, i);
    Assert.assertEquals("00000000FF20FFFF", BinaryUtil.binary2Hex(BinaryUtil.getBinary(l)));
	}
}
