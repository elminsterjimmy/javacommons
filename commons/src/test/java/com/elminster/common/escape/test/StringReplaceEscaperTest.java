package com.elminster.common.escape.test;

import com.elminster.common.escape.Escaper;
import com.elminster.common.escape.EscaperBuilder;
import org.junit.Assert;
import org.junit.Test;

public class StringReplaceEscaperTest {

  @Test
  public void testEscape() {
    Escaper escaper1 = EscaperBuilder.newBuilder().addEscape("a", "b").build();
    Assert.assertEquals("bbcdefbbcdefbbc", escaper1.escape("abcdefabcdefabc"));
    Escaper escaper2 = EscaperBuilder.newBuilder().addEscape("*", "?").build();
    Assert.assertEquals("a?b?c+d?e", escaper2.escape("a*b*c+d*e"));
    Escaper escaper3 = EscaperBuilder.newBuilder().addEscape("abc", "?").build();
    Assert.assertEquals("?def?def?ab", escaper3.escape("abcdefabcdefabcab"));
    Escaper escaper4 = EscaperBuilder.newBuilder().addEscape("?", "abc").build();
    Assert.assertEquals("abcdefabcdefabcab", escaper4.escape("?def?def?ab"));
    Escaper escaper5 = EscaperBuilder.newBuilder()
        .addEscape("?", "abc")
        .addEscape("*", "bbc")
        .addEscape("ef", "&")
        .build();
    Assert.assertEquals("abcdebbcfabcd&abcab", escaper5.escape("?de*f?def?ab"));

  }
}
