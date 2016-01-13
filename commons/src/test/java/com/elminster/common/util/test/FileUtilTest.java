package com.elminster.common.util.test;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.elminster.common.util.FileUtil;

public class FileUtilTest {

  @Test
  public void testGetFileNameExcludeExtension() {
    File f = new File("c:/test/test.xml");
    try {
      if (!f.exists()) {
        f.createNewFile();
      }
      Assert.assertEquals(FileUtil.getFileNameExcludeExtension(f), "test");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      if (f.exists()) {
        f.delete();
      }
    }
  }
}
