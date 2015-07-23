package com.elminster.common.util.test;

import org.junit.Assert;
import org.junit.Test;

import com.elminster.common.util.StringUtil;


public class StringUtilTest {

  @Test
  public void testChomTrim() {
    String s1 = "\r\n abc \n\t";
    String s2 = "       abc     \r\n";
    String s3 = "abc";
    String s4 = "";
    String s5 = null;
    String s6 = "abc\r\nefg";
    Assert.assertEquals("abc", StringUtil.chompTrim(s1));
    Assert.assertEquals("abc", StringUtil.chompTrim(s2));
    Assert.assertEquals("abc", StringUtil.chompTrim(s3));
    Assert.assertEquals("", StringUtil.chompTrim(s4));
    Assert.assertEquals(null, StringUtil.chompTrim(s5));
    Assert.assertEquals("abc\r\nefg", StringUtil.chompTrim(s6));
  }
}
