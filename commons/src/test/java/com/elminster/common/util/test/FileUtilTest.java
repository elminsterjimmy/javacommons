package com.elminster.common.util.test;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.elminster.common.util.FileUtil;

public class FileUtilTest {

  @Test
  public void testGetFileNameExcludeExtension() {
    File f = new File("c:/test.xml");
    try {
      if (!f.exists()) {
        f.createNewFile();
      }
      Assert.assertEquals(FileUtil.getFileNameExcludeExtension(f), "test");
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (f.exists()) {
        f.delete();
      }
    }
  }
  
  @Test
  public void testToSafeFileName() {
    Assert.assertEquals("", FileUtil.toSafeFileName(null));
    Assert.assertEquals("testFileName_1-1,.{}[]()=+&^%$#@!~", FileUtil.restoreFromSafeFileName(FileUtil.toSafeFileName("testFileName_1-1,.{}[]()=+&^%$#@!~")));
    Assert.assertEquals("#2F#5C#7C#3F#2A#3C#3E#22##", FileUtil.toSafeFileName("/\\|?*<>\"#"));
    Assert.assertEquals("/\\|?*<>\"#", FileUtil.restoreFromSafeFileName(FileUtil.toSafeFileName("/\\|?*<>\"#")));
    Assert.assertEquals("#中文测试#", FileUtil.restoreFromSafeFileName(FileUtil.toSafeFileName("#中文测试#")));
    Assert.assertEquals("日本語テスト/", FileUtil.restoreFromSafeFileName(FileUtil.toSafeFileName("日本語テスト/")));
  }
}
