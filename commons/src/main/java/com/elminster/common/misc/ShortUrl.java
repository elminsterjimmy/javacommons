package com.elminster.common.misc;

import com.elminster.common.util.EncryptUtil;

/**
 * The Utility class for generate the short URL.
 * 
 * 1. Use MD5 to generate a length 32 hex string.
 * 2. Split the string into 4 6-length hex string
 * 3. AND with "0x3FFFFFFF" every 6-length hex string
 * 4. Split it into 6 5-bit index
 * 5. look at the CHAR_MAP to get a 6-length string
 * 
 * @author jgu
 * @version 1.0
 */
abstract public class ShortUrl {

  /** The mixed string. */
  private static final String MIXED_STRING = "elminster.jimmy";

  /** The char map. */
  private static final char[] CHAR_MAP =
      new char[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
          'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
          '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
          'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
          'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '-' };

  public static void main(String[] args) throws Exception {
    String url = "http://www.google.com";
    for (String string : generateShortURL(url)) {
      System.out.println(string);
    }
  }

  /**
   * Get the shortUrl of the string.
   * @param string the string
   * @return short URL
   * @throws Exception
   */
  public static String[] generateShortURL(String string) throws Exception {
    String hex = EncryptUtil.encryptMD5((MIXED_STRING + string).getBytes());
    int hexLen = hex.length();
    int subHexLen = hexLen / 8;
    String[] ShortStr = new String[4];
    StringBuilder outChars = new StringBuilder(6);
    for (int i = 0; i < subHexLen; i++) {
      outChars.delete(0, 6);
      int j = i + 1;
      String subHex = hex.substring(i * 8, j * 8);
      long idx = 0x3FFFFFFF & Long.valueOf(subHex, 16);

      for (int k = 0; k < 6; k++) {
        int index = (int) (0x3F & idx);
        outChars.append(CHAR_MAP[index]);
        idx >>>= 5;
      }
      ShortStr[i] = outChars.toString();
    }

    return ShortStr;
  }
}
