package com.elminster.common.escape.test;

import com.elminster.common.escape.RegexEscaper;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;

public class RegexEscaperTest {

  @Test
  public void testEscape() {
    RegexEscaper escaper = new RegexEscaper();
    String string = "(a*b^c) * [(d+e-f),(g/h)] ?";
    String unescaped = string;
    String escaped = escaper.escape(string);
    Pattern unescapedPattern = Pattern.compile(unescaped);
    Pattern escapedPattern = Pattern.compile(escaped);
    Assert.assertFalse(unescapedPattern.matcher(string).find());
    Assert.assertTrue(escapedPattern.matcher(string).find());
  }
}
