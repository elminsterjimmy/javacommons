package com.elminster.common.util;

import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Encrypt Utilities
 * 
 * @author Gu
 * @version 1.0
 * 
 */
@SuppressWarnings({"restriction"})
public class EncryptUtil {

  /** MD5. */
  public static final String MD5 = "MD5"; //$NON-NLS-1$

  /** SHA-1. */
  public static final String SHA = "SHA-1"; //$NON-NLS-1$

  /** DES. */
  public static final String DES = "DES"; //$NON-NLS-1$

  public static String encryptBASE64(byte[] key) throws Exception {
    return (new BASE64Encoder()).encodeBuffer(key);
  }

  public static byte[] decryptBASE64(String key) throws Exception {
    return (new BASE64Decoder()).decodeBuffer(key);
  }

  public static String encryptMD5(byte[] data) throws Exception {
    return digest(data, MD5);
  }

  public static String encryptSHA(byte[] data) throws Exception {
    return digest(data, SHA);
  }

  public static String digest(byte[] data, String type) throws Exception {
    MessageDigest digester = MessageDigest.getInstance(type);
    digester.update(data);
    byte[] digest = digester.digest();
    return BinaryUtil.binary2Hex(digest);
  }

  private static Key toKey(byte[] key) throws Exception {
    DESKeySpec dks = new DESKeySpec(key);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
    SecretKey secretKey = keyFactory.generateSecret(dks);
    return secretKey;
  }

  public static byte[] encryptDES(byte[] data, String key) throws Exception {
    Key k = toKey(decryptBASE64(key));
    Cipher cipher = Cipher.getInstance(DES);
    cipher.init(Cipher.ENCRYPT_MODE, k);
    return cipher.doFinal(data);
  }

  public static byte[] decryptDES(byte[] data, String key) throws Exception {
    Key k = toKey(decryptBASE64(key));
    Cipher cipher = Cipher.getInstance(DES);
    cipher.init(Cipher.DECRYPT_MODE, k);
    return cipher.doFinal(data);
  }

  public static String initKey(String seed) throws Exception {
    SecureRandom secureRandom = null;
    if (null != seed) {
      secureRandom = new SecureRandom(decryptBASE64(seed));
    } else {
      secureRandom = new SecureRandom();
    }
    KeyGenerator kg = KeyGenerator.getInstance(DES);
    kg.init(secureRandom);
    SecretKey secretKey = kg.generateKey();
    return encryptBASE64(secretKey.getEncoded());
  }
}
