package com.elminster.common.util;

import java.io.InputStream;
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
@SuppressWarnings({ "restriction" })
public class EncryptUtil {

  /** MD5. */
  public static final String MD5 = "MD5"; //$NON-NLS-1$

  /** SHA-1. */
  public static final String SHA = "SHA-1"; //$NON-NLS-1$

  /** DES. */
  public static final String DES = "DES"; //$NON-NLS-1$

  /**
   * Encrypt data with BASE64.
   * @param data the data
   * @return encrypted data
   * @throws Exception on error
   */
  public static String encryptBASE64(byte[] data) throws Exception {
    return (new BASE64Encoder()).encodeBuffer(data);
  }

  /**
   * Decrypt the data with BASE64.
   * @param data the data 
   * @return the decrypted data
   * @throws Exception on error
   */
  public static byte[] decryptBASE64(String data) throws Exception {
    return (new BASE64Decoder()).decodeBuffer(data);
  }

  /**
   * Encrypt the data with MD5.
   * @param data the data
   * @return encrypt the data
   * @throws Exception on error
   */
  public static String encryptMD5(byte[] data) throws Exception {
    return digest(data, MD5);
  }

  /**
   * Encrypt the data with MD5.
   * @param in the data
   * @return encrypt the data
   * @throws Exception on error
   */
  public static String encryptMD5(InputStream in) throws Exception {
    return digest(in, MD5);
  }

  /**
   * Encrypt the data with SHA.
   * @param data the data
   * @return encrypt the data
   * @throws Exception on error
   */
  public static String encryptSHA(byte[] data) throws Exception {
    return digest(data, SHA);
  }

  /**
   * Encrypt the data with SHA.
   * @param in the data
   * @return encrypt the data
   * @throws Exception on error
   */
  public static String encryptSHA(InputStream in) throws Exception {
    return digest(in, SHA);
  }

  /**
   * Digest the data with specified algorithm.
   * @param data the data
   * @param type the algorithm type
   * @return the digested data
   * @throws Exception on error
   */
  public static String digest(byte[] data, String type) throws Exception {
    MessageDigest digester = MessageDigest.getInstance(type);
    digester.update(data);
    byte[] digest = digester.digest();
    return BinaryUtil.binary2Hex(digest);
  }
  
  /**
   * Digest the data with specified algorithm.
   * @param in the data
   * @param type the algorithm type
   * @return the digested data
   * @throws Exception on error
   */
  public static String digest(InputStream in, String type) throws Exception {
    MessageDigest digester = MessageDigest.getInstance(type);
    int read = 0;
    byte[] buf = new byte[1024 * 16];
    while ((read = in.read(buf)) > 0) {
      digester.update(buf, 0, read);
    }
    byte[] digest = digester.digest();
    return BinaryUtil.binary2Hex(digest);
  }

  /**
   * Generate the DES key.
   * @param key the key to generate
   * @return the key
   * @throws Exception on error
   */
  private static Key toKey(byte[] key) throws Exception {
    DESKeySpec dks = new DESKeySpec(key);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
    SecretKey secretKey = keyFactory.generateSecret(dks);
    return secretKey;
  }

  /**
   * Encrypt the data with DES by the specified key.
   * @param data the data
   * @param key the key
   * @return encrypted data
   * @throws Exception on error
   */
  public static byte[] encryptDES(byte[] data, String key) throws Exception {
    Key k = toKey(decryptBASE64(key));
    Cipher cipher = Cipher.getInstance(DES);
    cipher.init(Cipher.ENCRYPT_MODE, k);
    return cipher.doFinal(data);
  }

  /**
   * Decrypt the data with DES by the specified key.
   * @param data the data
   * @param key the key
   * @return decrypted data
   * @throws Exception on error
   */
  public static byte[] decryptDES(byte[] data, String key) throws Exception {
    Key k = toKey(decryptBASE64(key));
    Cipher cipher = Cipher.getInstance(DES);
    cipher.init(Cipher.DECRYPT_MODE, k);
    return cipher.doFinal(data);
  }

  /**
   * Generate a key.
   * @param seed the seed
   * @return generated key
   * @throws Exception on error
   */
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
