package com.elminster.common.util;

import static com.elminster.common.constants.Constants.StringConstants.EMPTY_STRING;
import com.elminster.common.util.Messages.Message;

/**
 * Binary Utilities
 * 
 * @author Gu
 * @version 1.0
 */
public abstract class BinaryUtil {

  /** BYTE OFFSET */
  private static final int BYTE_OFFSET = 128;

  /** Size of 16-Hex */
  private static final int HEX_SIZE = 4;

  /** String of Binary(1~16) */
  private static final String[] BINARY_INDEX = { 
      "0000", "0001", "0010", "0011", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
      "0100", "0101", "0110", "0111", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
      "1000", "1001", "1010", "1011", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
      "1100", "1101", "1110", "1111" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
  };

  /** String of 16 Hex */
  private static final String[] HEX_INDEX = {
      "0", "1", "2", "3", "4", "5", "6", "7", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
      "8", "9", "A", "B", "C", "D", "E", "F" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
  };

  /** <code>0</code>. */
  private static final String ZERO = "0"; //$NON-NLS-1$

  /**
   * Convert specified byte array to integer (Big Endian).
   * 
   * @param binary
   *          byte array
   * @param offset
   *          convert start offset
   * @param length
   *          convert length
   * @return converted integer
   */
  public static int binary2IntInBigEndian(byte[] binary, int offset, int length) {
    if (!checkBound(binary, offset, length)) {
      throw new IllegalArgumentException(Messages.getString(Message.OUT_OF_BOUND));
    }
    if (length * Byte.SIZE > Integer.SIZE) {
      throw new IllegalArgumentException(Messages.getString(Message.OVER_INTEGER_RANGE));
    }
    int intVal = 0;
    for (int i = 0; i < length; i++) {
      byte b = binary[offset + i];
      int v = ((int) b + BYTE_OFFSET) << (Byte.SIZE * (length - 1 - i));
      v = reverseBit(v, Byte.SIZE * (length - i) - 1);
      intVal += v;
    }
    return intVal;
  }

  /**
   * Convert byte array to long (Big Endian).
   * 
   * @param binary
   *          byte array
   * @param offset
   *          convert start offset
   * @param length
   *          convert length
   * @return converted long
   */
  public static long binary2LongInBigEndian(byte[] binary, int offset, int length) {
    if (!checkBound(binary, offset, length)) {
      throw new IllegalArgumentException(Messages.getString(Message.OUT_OF_BOUND));
    }
    if (length * Byte.SIZE > Long.SIZE) {
      throw new IllegalArgumentException(Messages.getString(Message.OVER_LONG_RANGE));
    }
    long intVal = 0L;
    for (int i = 0; i < length; i++) {
      byte b = binary[offset + i];
      long v = ((int) b + BYTE_OFFSET) << (Byte.SIZE * (length - 1 - i));
      v = reverseBit(v, Byte.SIZE * (length - i) - 1);
      intVal += v;
    }
    return intVal;
  }

  /**
   * Check the specified byte array whether out of array bound.
   * 
   * @param binary
   *          specified byte array
   * @param offset
   *          start offset of the specified byte array
   * @param length
   *          length
   * @return whether in the range of array bound
   */
  private static boolean checkBound(byte[] binary, int offset, int length) {
    int binaryLen = binary.length;
    if (offset < 0 || offset + length > binaryLen) {
      return false;
    }
    return true;
  }

  /**
   * Convert binary string to hex string.
   * 
   * @param binaryString
   *          binary string
   * @return 16 hex string
   */
  public static String binary2Hex(String binaryString) {
    if (null == binaryString) {
      throw new IllegalArgumentException();
    }
    int length = binaryString.length();
    if (0 != length % HEX_SIZE) {
      int paddingCount = HEX_SIZE - length / HEX_SIZE;
      for (int i = 0; i < paddingCount; i++) {
        binaryString = ZERO + binaryString;
      }
    }
    StringBuilder sb = new StringBuilder(binaryString.length() / HEX_SIZE);
    for (int i = 0; i < binaryString.length() / HEX_SIZE; i++) {
      String idx = binaryString.substring(HEX_SIZE * i, HEX_SIZE * (i + 1));
      for (int j = 0; j < BINARY_INDEX.length; j++) {
        if (BINARY_INDEX[j].equals(idx)) {
          sb.append(HEX_INDEX[j]);
          break;
        }
      }
    }
    return sb.toString();
  }
  
  /**
   * Return a String representation of the specified byte as binary.
   * 
   * @param val
   *          the specified byte
   * @return string representation of the specified byte as binary
   */
  public static String getBinary(final byte val) {
    StringBuilder sb = new StringBuilder(Integer.SIZE);
    for (int i = Byte.SIZE - 1; i >= 0; i--) {
      sb.append((val >> i) & 0x01);
    }
    return sb.toString();
  }
  
  /**
   * Return a String representation of the specified byte as hex.
   * 
   * @param val
   *          the specified byte
   * @return string representation of the specified byte as hex
   */
  public static String getHex(final byte val) {
    return binary2Hex(getBinary(val));
  }
  
  /**
   * Return a String representation of the specified integer as binary.
   * 
   * @param val
   *          the specified integer
   * @return string representation of the specified integer as binary
   */
  public static String getBinary(final int val) {
    StringBuilder sb = new StringBuilder(Integer.SIZE);
    for (int i = Integer.SIZE - 1; i >= 0; i--) {
      sb.append((val >> i) & 0x01);
    }
    return sb.toString();
  }
  
  /**
   * Return a String representation of the specified integer as hex.
   * 
   * @param val
   *          the specified byte
   * @return string representation of the specified byte as hex
   */
  public static String getHex(final int val) {
    return binary2Hex(getBinary(val));
  }

  /**
   * Return a String representation of the specified long as binary.
   * 
   * @param val
   *          the specified long
   * @return String representation of the specified long as binary
   */
  public static String getBinary(final long val) {
    StringBuilder sb = new StringBuilder(Long.SIZE);
    for (int i = Long.SIZE - 1; i >= 0; i--) {
      sb.append((val >> i) & 0x01);
    }
    return sb.toString();
  }
  
  /**
   * Return a String representation of the specified long as hex.
   * 
   * @param val
   *          the specified byte
   * @return string representation of the specified byte as hex
   */
  public static String getHex(final long val) {
    return binary2Hex(getBinary(val));
  }

  /**
   * Reverse the specified position bit in an integer.
   * 
   * @param val
   *          specified integer what to be reverse
   * @param position
   *          specified position(0~31 from right) in the integer
   * @return reversed integer
   */
  public static int reverseBit(final int val, final int position) {
    int v = setBit(0, position);
    return val ^ v;
  }

  /**
   * Reverse the specified position bit in a long.
   * 
   * @param val
   *          specified long what to be reverse
   * @param position
   *          specified position(0~63 from right) in the long
   * @return reversed long
   */
  public static long reverseBit(final long val, final int position) {
    long v = setBit((long) 0, position);
    return val ^ v;
  }
  
  /**
   * Check whether the bit at the specified position in an integer is "1".
   * 
   * @param val
   *          specified integer what to be checked
   * @param position
   *          specified position(0~31 from right) in the integer
   * @return whether the bit at the specified position in an integer is "1"
   */
  public static boolean isSet(final int val, final int position) {
    return 1 == ((val >> position) & 0x01);
  }

  /**
   * Check whether the bit at the specified position in a long is "1".
   * 
   * @param val
   *          specified long what to be checked
   * @param position
   *          specified position(0~63 from right) in the long
   * @return whether the bit at the specified position in a long is "1"
   */
  public static boolean isSet(final long val, final int position) {
    return 1 == ((val >> position) & 0x01);
  }

  /**
   * Set the specified position in an integer to "0".
   * 
   * @param val
   *          an integer what to be set
   * @param position
   *          specified position(0~31 from right) in the integer
   * @return the integer is set
   */
  public static int resetBit(final int val, final int position) {
    return val & ~(0x01 << position);
  }

  /**
   * Set the specified position in a long to "0".
   * 
   * @param val
   *          a long what to be set
   * @param position
   *          specified position(0~63 from right) in the long
   * @return the long is set
   */
  public static long resetBit(final long val, final int position) {
    return val & ~(((long) 0x01) << position);
  }

  /**
   * Set the specified position in an integer to "1".
   * 
   * @param val
   *          an integer what to be set
   * @param position
   *          specified position(0~31 from right) in the integer
   * @return the integer is set
   */
  public static int setBit(final int val, final int position) {
    return val | (0x01 << position);
  }
  
  /**
   * Set the specified position in a long to "1".
   * 
   * @param val
   *          a long what to be set
   * @param position
   *          specified position(0~63 from right) in the long
   * @return the long is set
   */
  public static long setBit(final long val, final int position) {
    return val | (((long) 0x01) << position);
  }

  /**
   * Convert byte array to String.
   * 
   * @param binary
   *          specified byte array
   * @param offset
   *          start offset of the specified byte array
   * @param length
   *          length
   * @param charset
   *          the charset of the String
   * @return converted String
   * @throws Exception
   *           exception
   */
  public static String binary2String(byte[] binary, int offset, int length, String charset) throws Exception {
    byte[] bytes = new byte[length];
    System.arraycopy(binary, offset, bytes, 0, length);
    return new String(bytes, charset);
  }

  /**
   * Convert byte array to String in 16-Hex.
   * 
   * @param binary
   *          specified byte array
   * @return converted String
   */
  public static String binary2Hex(byte[] binary) {
    String hs = EMPTY_STRING;
    String stmp = EMPTY_STRING;
    if (null != binary) {
      for (byte bt : binary) {
        stmp = Integer.toHexString(bt & 0xFF);
        if (stmp.length() == 1) {
          hs = hs + ZERO + stmp;
        } else {
          hs = hs + stmp;
        }
      }
    }
    return hs.toUpperCase();
  }
  
  /**
   * Convert String in 16-Hex to byte array.
   * 
   * @param hex
   *          specified String in 16-Hex
   * @return byte array
   */
  public static byte[] hex2Binary(String hex) {
    Assert.notNull(hex);
    byte[] binary = null;
    if (null != hex) {
      int length = hex.length();
      if (0 != length % 2) {
        // add 0 at the header
        hex = "0" + hex;
      }
      binary = new byte[length / 2];
      for (int i = 0; i < hex.length(); i = i + 2) {
        char high = hex.charAt(i);
        char low = hex.charAt(i + 1);
        byte b = ((Integer)Integer.parseInt("" + high + low, 16)).byteValue();
        binary[i / 2] = b;
      }
    }
    return binary;
  }
  
  /**
   * short to byte array.
   * 
   * @param v short value.
   * @return byte[].
   */
  public static byte[] short2bytes(short v) {
    byte[] ret = { 0, 0 };
    short2bytes(v, ret);
    return ret;
  }

  /**
   * short to byte array.
   * 
   * @param v short value.
   * @param b byte array.
   */
  public static void short2bytes(short v, byte[] b) {
    short2bytes(v, b, 0);
  }

  /**
   * short to byte array.
   * 
   * @param v short value.
   * @param b byte array.
   */
  public static void short2bytes(short v, byte[] b, int off) {
    b[off + 1] = (byte) v;
    b[off + 0] = (byte) (v >>> 8);
  }

  /**
   * int to byte array.
   * 
   * @param v int value.
   * @return byte[].
   */
  public static byte[] int2bytes(int v) {
    byte[] ret = { 0, 0, 0, 0 };
    int2bytes(v, ret);
    return ret;
  }

  /**
   * int to byte array.
   * 
   * @param v int value.
   * @param b byte array.
   */
  public static void int2bytes(int v, byte[] b) {
    int2bytes(v, b, 0);
  }

  /**
   * int to byte array.
   * 
   * @param v int value.
   * @param b byte array.
   * @param off array offset.
   */
  public static void int2bytes(int v, byte[] b, int off) {
    b[off + 3] = (byte) v;
    b[off + 2] = (byte) (v >>> 8);
    b[off + 1] = (byte) (v >>> 16);
    b[off + 0] = (byte) (v >>> 24);
  }

  /**
   * float to byte array.
   * 
   * @param v float value.
   * @return byte[].
   */
  public static byte[] float2bytes(float v) {
    byte[] ret = { 0, 0, 0, 0 };
    float2bytes(v, ret);
    return ret;
  }

  /**
   * float to byte array.
   * 
   * @param v float value.
   * @param b byte array.
   */
  public static void float2bytes(float v, byte[] b) {
    float2bytes(v, b, 0);
  }

  /**
   * float to byte array.
   * 
   * @param v float value.
   * @param b byte array.
   * @param off array offset.
   */
  public static void float2bytes(float v, byte[] b, int off) {
    int i = Float.floatToIntBits(v);
    b[off + 3] = (byte) i;
    b[off + 2] = (byte) (i >>> 8);
    b[off + 1] = (byte) (i >>> 16);
    b[off + 0] = (byte) (i >>> 24);
  }

  /**
   * long to byte array.
   * 
   * @param v long value.
   * @return byte[].
   */
  public static byte[] long2bytes(long v) {
    byte[] ret = { 0, 0, 0, 0, 0, 0, 0, 0 };
    long2bytes(v, ret);
    return ret;
  }

  /**
   * long to byte array.
   * 
   * @param v long value.
   * @param b byte array.
   */
  public static void long2bytes(long v, byte[] b) {
    long2bytes(v, b, 0);
  }

  /**
   * long to byte array.
   * 
   * @param v long value.
   * @param b byte array.
   * @param off array offset.
   */
  public static void long2bytes(long v, byte[] b, int off) {
    b[off + 7] = (byte) v;
    b[off + 6] = (byte) (v >>> 8);
    b[off + 5] = (byte) (v >>> 16);
    b[off + 4] = (byte) (v >>> 24);
    b[off + 3] = (byte) (v >>> 32);
    b[off + 2] = (byte) (v >>> 40);
    b[off + 1] = (byte) (v >>> 48);
    b[off + 0] = (byte) (v >>> 56);
  }

  /**
   * double to byte array.
   * 
   * @param v double value.
   * @return byte[].
   */
  public static byte[] double2bytes(double v) {
    byte[] ret = { 0, 0, 0, 0, 0, 0, 0, 0 };
    double2bytes(v, ret);
    return ret;
  }

  /**
   * double to byte array.
   * 
   * @param v double value.
   * @param b byte array.
   */
  public static void double2bytes(double v, byte[] b) {
    double2bytes(v, b, 0);
  }

  /**
   * double to byte array.
   * 
   * @param v double value.
   * @param b byte array.
   * @param off array offset.
   */
  public static void double2bytes(double v, byte[] b, int off) {
    long j = Double.doubleToLongBits(v);
    b[off + 7] = (byte) j;
    b[off + 6] = (byte) (j >>> 8);
    b[off + 5] = (byte) (j >>> 16);
    b[off + 4] = (byte) (j >>> 24);
    b[off + 3] = (byte) (j >>> 32);
    b[off + 2] = (byte) (j >>> 40);
    b[off + 1] = (byte) (j >>> 48);
    b[off + 0] = (byte) (j >>> 56);
  }

  /**
   * bytes to short.
   * 
   * @param b byte array.
   * @return short.
   */
  public static short bytes2short(byte[] b) {
    return bytes2short(b, 0);
  }

  /**
   * bytes to short.
   * 
   * @param b byte array.
   * @param off offset.
   * @return short.
   */
  public static short bytes2short(byte[] b, int off) {
    return (short) (((b[off + 1] & 0xFF) << 0) +
      ((b[off + 0]) << 8));
  }

  /**
   * bytes to int.
   * 
   * @param b byte array.
   * @return int.
   */
  public static int bytes2int(byte[] b) {
    return bytes2int(b, 0);
  }

  /**
   * bytes to int.
   * 
   * @param b byte array.
   * @param off offset.
   * @return int.
   */
  public static int bytes2int(byte[] b, int off) {
    return ((b[off + 3] & 0xFF) << 0) +
         ((b[off + 2] & 0xFF) << 8) +
         ((b[off + 1] & 0xFF) << 16) +
         ((b[off + 0]) << 24);
  }

  /**
   * bytes to float.
   * 
   * @param b byte array.
   * @return float.
   */
  public static float bytes2float(byte[] b) {
    return bytes2float(b, 0);
  }

  /**
   * bytes to float.
   * 
   * @param b byte array.
   * @param off offset.
   * @return float.
   */
  public static float bytes2float(byte[] b, int off) {
    int i = ((b[off + 3] & 0xFF) << 0) +
      ((b[off + 2] & 0xFF) << 8) +
      ((b[off + 1] & 0xFF) << 16) +
      ((b[off + 0]) << 24);
    return Float.intBitsToFloat(i);
  }

  /**
   * byte to long.
   * 
   * @param b byte array.
   * @return long.
   */
  public static long bytes2long(byte[] b)  {
    return bytes2long(b,0);
  }

  /**
   * bytes to long.
   * 
   * @param b byte array.
   * @param off offset.
   * @return long.
   */
  public static long bytes2long(byte[] b,int off) {
    return ((b[off + 7] & 0xFFL) << 0) +
         ((b[off + 6] & 0xFFL) << 8) +
         ((b[off + 5] & 0xFFL) << 16) +
         ((b[off + 4] & 0xFFL) << 24) +
         ((b[off + 3] & 0xFFL) << 32) +
         ((b[off + 2] & 0xFFL) << 40) +
         ((b[off + 1] & 0xFFL) << 48) +
         (((long) b[off + 0]) << 56);
  }

  /**
   * bytes to double.
   * 
   * @param b byte array.
   * @return double.
   */
  public static double bytes2double(byte[] b) {
    return bytes2double(b,0);
  }

  /**
   * bytes to double.
   * 
   * @param b byte array.
   * @param off offset.
   * @return double.
   */
  public static double bytes2double(byte[] b, int off)  {
    long j = ((b[off + 7] & 0xFFL) << 0) +
       ((b[off + 6] & 0xFFL) << 8) +
       ((b[off + 5] & 0xFFL) << 16) +
       ((b[off + 4] & 0xFFL) << 24) +
       ((b[off + 3] & 0xFFL) << 32) +
       ((b[off + 2] & 0xFFL) << 40) +
       ((b[off + 1] & 0xFFL) << 48) +
       (((long) b[off + 0]) << 56);
    return Double.longBitsToDouble(j);
  }
  
  /**
   * Concat bytes.
   * @param bytes the bytes
   * @return concated byte
   */
  public static byte[] concatBytes(byte[]... bytes) {
    int length = 0;
    for (byte[] b : bytes) {
      length += b.length;
    }
    byte[] result = new byte[length];
    int off = 0;
    for (byte[] b : bytes) {
      System.arraycopy(b, 0, result, off, b.length);
      off += b.length;
    }
    return result;
  }
}
