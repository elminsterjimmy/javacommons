package com.elminster.common.util.test;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.elminster.common.util.FileUtil;

public class FileUtilTest {

  @Test
  public void testGetFileNameExcludeExtension() {
    File f = new File("test.xml");
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
  
  @Test
  public void testFindFileFrom() {
    String fileName = "test1/test2/test3.txt";
    File file = new File(fileName);
    try {
      FileUtil.createNewFile(fileName);
      File root = new File("test1");
      File foundFile3 = FileUtil.findFileFrom(root, "test3.txt");
      File foundFile2 = FileUtil.findFileFrom(root, "test2");
      File foundFile4 = FileUtil.findFileFrom(root, "test4.txt");
      File expactFile2 = new File("test1/test2");
      Assert.assertEquals(file.getAbsolutePath(), foundFile3.getAbsolutePath());
      Assert.assertEquals(expactFile2.getAbsolutePath(), foundFile2.getAbsolutePath());
      Assert.assertEquals(null, foundFile4);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (file.exists()) {
        file.delete();
        FileUtil.deleteEmptyFolder("test1");
      }
    }
  }
  
  @Test
  public void testGetFileNameAndPath() {
    String unix = "/test/test1/test2.txt";
    String win = "c:\\test\\test1\\test2.txt";
    String folder = "/test/test1/";
    Assert.assertEquals("test2.txt", FileUtil.getFileName(unix));
    Assert.assertEquals("test2.txt", FileUtil.getFileName(win));
    Assert.assertEquals("", FileUtil.getFileName(folder));
    
    Assert.assertEquals("/test/test1", FileUtil.getParentDirectory(unix));
    Assert.assertEquals("c:\\test\\test1", FileUtil.getParentDirectory(win));
    Assert.assertEquals("/test/test1", FileUtil.getParentDirectory(folder));
  }
}
