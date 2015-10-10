package com.elminster.common.util.test;

import org.junit.Assert;
import org.junit.Test;

import com.elminster.common.util.BinaryUtil;
import com.elminster.common.util.EncryptUtil;

public class EncryptUtilTest {

  @Test
  public void testDES() throws Exception {
    String data = "test DES encrypt";
    String seed = "DES seed";
    String key = EncryptUtil.initDESKey(seed);
    byte[] encrypted = EncryptUtil.encryptDES(data.getBytes(), key);
    System.out.println(BinaryUtil.binary2Hex(encrypted));
    byte[] decrypted = EncryptUtil.decryptDES(encrypted, key);
    Assert.assertEquals(data, new String(decrypted));
  }
  
  @Test
  public void testAES() throws Exception {
    String data = "test AES encrypt";
    String seed = "AES seed";
    byte[] key = EncryptUtil.initAESKey(seed);
    byte[] encrypted = EncryptUtil.encryptAES(data.getBytes(), key);
    byte[] decrypted = EncryptUtil.decryptAES(encrypted, key);
    Assert.assertEquals(data, new String(decrypted));
  }
  
  @Test
  public void testMD5() throws Exception {
    String md5test = "test md5";
    String encrypted = EncryptUtil.encryptMD5(md5test.getBytes());
    Assert.assertEquals(16 * 2, encrypted.length());
  }
  
  @Test
  public void testSHA() throws Exception {
    String shatest = "test sha";
    String encrypted = EncryptUtil.encryptSHA(shatest.getBytes());
    Assert.assertEquals(20 * 2, encrypted.length());
  }
  
  @Test
  public void testSHA256() throws Exception {
    String shatest = "test sha";
    String encrypted = EncryptUtil.encryptSHA256(shatest.getBytes());
    Assert.assertEquals(32 * 2, encrypted.length());
  }
  
  @Test
  public void testSHA512() throws Exception {
    String shatest = "test sha";
    String encrypted = EncryptUtil.encryptSHA512(shatest.getBytes());
    Assert.assertEquals(64 * 2, encrypted.length());
  }
}
