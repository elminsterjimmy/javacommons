package com.elminster.common.util;

import java.io.InputStream;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Encrypt Utilities
 * 
 * @author Gu
 * @version 1.0
 * 
 */
public class EncryptUtil {

  /** MD5. */
  public static final String MD5 = "MD5"; //$NON-NLS-1$

  /** SHA-1. */
  public static final String SHA = "SHA"; //$NON-NLS-1$
  
  /** SHA-256. */
  public static final String SHA256 = "SHA-256"; //$NON-NLS-1$

  /** SHA-512. */
  public static final String SHA512 = "SHA-512"; //$NON-NLS-1$

  /** DES. */
  public static final String DES = "DES"; //$NON-NLS-1$
  
  /** AES. */
  public static final String AES = "AES"; //$NON-NLS-1$
  
  /** AES CIPHER ALGORITHM. */
  private static final String AES_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding"; //$NON-NLS-1$

  /**
   * Encrypt data with BASE64.
   * @param data the data
   * @return encrypted data
   * @throws Exception on error
   */
  public static String encryptBASE64(byte[] data) throws Exception {
    return Base64.getEncoder().encodeToString(data);
  }

  /**
   * Decrypt the data with BASE64.
   * @param data the data 
   * @return the decrypted data
   * @throws Exception on error
   */
  public static byte[] decryptBASE64(String data) throws Exception {
    return Base64.getDecoder().decode(data);
  }

  /**
   * Encrypt the data with MD5.
   * @param data the data
   * @return encrypt the data (16bytes)
   * @throws Exception on error
   */
  public static String encryptMD5(byte[] data) throws Exception {
    return digest(data, MD5);
  }
  
  /**
   * Encrypt the data with MD5.
   * @param in the data
   * @return encrypt the data (16bytes)
   * @throws Exception on error
   */
  public static String encryptMD5(InputStream in) throws Exception {
    return digest(in, MD5);
  }

  /**
   * Encrypt the data with SHA.
   * @param data the data
   * @return encrypt the data (20bytes)
   * @throws Exception on error
   */
  public static String encryptSHA(byte[] data) throws Exception {
    return digest(data, SHA);
  }
  
  /**
   * Encrypt the data with SHA.
   * @param in the data
   * @return encrypt the data (20bytes)
   * @throws Exception on error
   */
  public static String encryptSHA(InputStream in) throws Exception {
    return digest(in, SHA);
  }
  
  /**
   * Encrypt the data with SHA-256.
   * @param data the data
   * @return encrypt the data (32bytes)
   * @throws Exception on error
   */
  public static String encryptSHA256(byte[] data) throws Exception {
    return digest(data, SHA256);
  }

  /**
   * Encrypt the data with SHA-256.
   * @param in the data
   * @return encrypt the data (32bytes)
   * @throws Exception on error
   */
  public static String encryptSHA256(InputStream in) throws Exception {
    return digest(in, SHA256);
  }
  
  /**
   * Encrypt the data with SHA-512.
   * @param data the data
   * @return encrypt the data (64bytes)
   * @throws Exception on error
   */
  public static String encryptSHA512(byte[] data) throws Exception {
    return digest(data, SHA512);
  }

  /**
   * Encrypt the data with SHA-512.
   * @param in the data
   * @return encrypt the data (64bytes)
   * @throws Exception on error
   */
  public static String encryptSHA512(InputStream in) throws Exception {
    return digest(in, SHA512);
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
    byte[] dested = digester.digest();
    return BinaryUtil.binary2Hex(dested);
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
  private static Key toDESKey(byte[] key) throws Exception {
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
    Key k = toDESKey(decryptBASE64(key));
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
    Key k = toDESKey(decryptBASE64(key));
    Cipher cipher = Cipher.getInstance(DES);
    cipher.init(Cipher.DECRYPT_MODE, k);
    return cipher.doFinal(data);
  }

  /**
   * Generate a DES key.
   * @param seed the seed
   * @return generated key
   * @throws Exception on error
   */
  public static String initDESKey(String seed) throws Exception {
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
  
  /**
   * Generate a AES key.
   * @return generated key
   * @throws Exception on error
   */
  public static byte[] initAESKey(String seed) throws Exception {
    KeyGenerator kg = null;
    kg = KeyGenerator.getInstance(AES);
    // AES needs 128 bit (16 byte) key use MD5 to generate it. 
    if (null != seed) {
      SecureRandom secureRandom = new SecureRandom(BinaryUtil.hex2Binary(encryptMD5(seed.getBytes())));
      kg.init(secureRandom);
    } else {
      kg.init(128);
    }
    SecretKey secretKey = kg.generateKey();
    return secretKey.getEncoded();
  }
  
  /**
   * Encrypt the data with AES by the specified key.
   * @param data the data
   * @param key the key
   * @return encrypted data
   * @throws Exception on error
   */
  public static byte[] encryptAES(byte[] data, byte[] key) throws Exception {
    Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
    cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, AES));
    return cipher.doFinal(data);
  }
  
  /**
   * Decrypt the data with AES by the specified key.
   * @param data the data
   * @param key the key
   * @return decrypted data
   * @throws Exception on error
   */
  public static byte[] decryptAES(byte[] data, byte[] key) throws Exception {
    Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
    cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, AES));
    return cipher.doFinal(data);
  }
}
