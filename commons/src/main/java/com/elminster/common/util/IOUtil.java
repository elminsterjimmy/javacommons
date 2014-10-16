package com.elminster.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import com.elminster.common.constants.Constants.EncodingConstants;

public class IOUtil {

  /**
   * The default buffer size to use.
   */
  private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
  
  /** The default encoding. UTF-8. */
  private static final String DEFAULT_ENCODING = EncodingConstants.UTF8;

  /**
   * Copy bytes from a large (over 2GB) <code>InputStream</code> to an
   * <code>OutputStream</code>.
   * <p>
   * This method buffers the input internally, so there is no need to use a
   * <code>BufferedInputStream</code>.
   * 
   * @param input the <code>InputStream</code> to read from
   * @param output the <code>OutputStream</code> to write to
   * @return the number of bytes copied
   * @throws NullPointerException if the input or output is null
   * @throws IOException if an I/O error occurs
   */
  public static long copyLarge(InputStream input, OutputStream output)
      throws IOException {
    byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
    long count = 0;
    int n = 0;
    while (-1 != (n = input.read(buffer))) {
      output.write(buffer, 0, n);
      count += n;
    }
    return count;
  }

  /**
   * Copy chars from a large (over 2GB) <code>Reader</code> to a
   * <code>Writer</code>.
   * <p>
   * This method buffers the input internally, so there is no need to use a
   * <code>BufferedReader</code>.
   * 
   * @param input the <code>Reader</code> to read from
   * @param output the <code>Writer</code> to write to
   * @return the number of characters copied
   * @throws NullPointerException if the input or output is null
   * @throws IOException if an I/O error occurs
   */
  public static long copyLarge(Reader input, Writer output) throws IOException {
    char[] buffer = new char[DEFAULT_BUFFER_SIZE];
    long count = 0;
    int n = 0;
    while (-1 != (n = input.read(buffer))) {
      output.write(buffer, 0, n);
      count += n;
    }
    return count;
  }

  /**
   * Copy bytes from an <code>InputStream</code> to an <code>OutputStream</code>
   * .
   * <p>
   * This method buffers the input internally, so there is no need to use a
   * <code>BufferedInputStream</code>.
   * <p>
   * Large streams (over 2GB) will return a bytes copied value of
   * <code>-1</code> after the copy has completed since the correct number of
   * bytes cannot be returned as an int. For large streams use the
   * <code>copyLarge(InputStream, OutputStream)</code> method.
   * 
   * @param input the <code>InputStream</code> to read from
   * @param output the <code>OutputStream</code> to write to
   * @return the number of bytes copied
   * @throws NullPointerException if the input or output is null
   * @throws IOException if an I/O error occurs
   * @throws ArithmeticException if the byte count is too large
   */
  public static int copy(InputStream input, OutputStream output)
      throws IOException {
    long count = copyLarge(input, output);
    if (count > Integer.MAX_VALUE) {
      return -1;
    }
    return (int) count;
  }

  /**
   * Copy bytes from an <code>InputStream</code> to chars on a
   * <code>Writer</code> using the default character encoding of the platform.
   * <p>
   * This method buffers the input internally, so there is no need to use a
   * <code>BufferedInputStream</code>.
   * <p>
   * This method uses {@link InputStreamReader}.
   * 
   * @param input the <code>InputStream</code> to read from
   * @param output the <code>Writer</code> to write to
   * @throws NullPointerException if the input or output is null
   * @throws IOException if an I/O error occurs
   */
  public static void copy(InputStream input, Writer output) throws IOException {
    InputStreamReader in = new InputStreamReader(input, "utf-8"); //$NON-NLS-1$
    copy(in, output);
  }

  /**
   * Copy chars from a <code>Reader</code> to a <code>Writer</code>.
   * <p>
   * This method buffers the input internally, so there is no need to use a
   * <code>BufferedReader</code>.
   * <p>
   * Large streams (over 2GB) will return a chars copied value of
   * <code>-1</code> after the copy has completed since the correct number of
   * chars cannot be returned as an int. For large streams use the
   * <code>copyLarge(Reader, Writer)</code> method.
   * 
   * @param input the <code>Reader</code> to read from
   * @param output the <code>Writer</code> to write to
   * @return the number of characters copied
   * @throws NullPointerException if the input or output is null
   * @throws IOException if an I/O error occurs
   * @throws ArithmeticException if the character count is too large
   */
  public static int copy(Reader input, Writer output) throws IOException {
    long count = copyLarge(input, output);
    if (count > Integer.MAX_VALUE) {
      return -1;
    }
    return (int) count;
  }

  /**
   * Copy bytes from an <code>InputStream</code> to chars on a
   * <code>Writer</code> using the specified character encoding.
   * <p>
   * This method buffers the input internally, so there is no need to use a
   * <code>BufferedInputStream</code>.
   * <p>
   * Character encoding names can be found at <a
   * href="http://www.iana.org/assignments/character-sets">IANA</a>.
   * <p>
   * This method uses {@link InputStreamReader}.
   * 
   * @param input the <code>InputStream</code> to read from
   * @param output the <code>Writer</code> to write to
   * @param encoding the encoding to use, null means platform default
   * @throws NullPointerException if the input or output is null
   * @throws IOException if an I/O error occurs
   */
  public static void copy(InputStream input, Writer output, String encoding)
      throws IOException {
    if (encoding == null) {
      copy(input, output);
    } else {
      InputStreamReader in = new InputStreamReader(input, encoding);
      copy(in, output);
    }
  }

  /**
   * Get the contents of an <code>InputStream</code> as a <code>byte[]</code>.
   * <p>
   * This method buffers the input internally, so there is no need to use a
   * <code>BufferedInputStream</code>.
   * 
   * @param input the <code>InputStream</code> to read from
   * @return the requested byte array
   * @throws NullPointerException if the input is null
   * @throws IOException if an I/O error occurs
   */
  public static byte[] toByteArray(InputStream input) throws IOException {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    copy(input, output);
    return output.toByteArray();
  }

  /**
   * Get the contents of an <code>InputStream</code> as a character array using
   * the default character encoding of the platform.
   * <p>
   * This method buffers the input internally, so there is no need to use a
   * <code>BufferedInputStream</code>.
   * 
   * @param is the <code>InputStream</code> to read from
   * @return the requested character array
   * @throws NullPointerException if the input is null
   * @throws IOException if an I/O error occurs
   * @since Commons IO 1.1
   */
  public static char[] toCharArray(InputStream is) throws IOException {
    CharArrayWriter output = new CharArrayWriter();
    copy(is, output);
    return output.toCharArray();
  }

  /**
   * Get the contents of an <code>InputStream</code> as a character array using
   * the specified character encoding.
   * <p>
   * Character encoding names can be found at <a
   * href="http://www.iana.org/assignments/character-sets">IANA</a>.
   * <p>
   * This method buffers the input internally, so there is no need to use a
   * <code>BufferedInputStream</code>.
   * 
   * @param is the <code>InputStream</code> to read from
   * @param encoding the encoding to use, null means platform default
   * @return the requested character array
   * @throws NullPointerException if the input is null
   * @throws IOException if an I/O error occurs
   */
  public static char[] toCharArray(InputStream is, String encoding)
      throws IOException {
    CharArrayWriter output = new CharArrayWriter();
    copy(is, output, encoding);
    return output.toCharArray();
  }

  /**
   * Convert the specified string to an input stream, encoded as bytes using the
   * default character encoding of the platform.
   * 
   * @param input the string to convert
   * @return an input stream
   */
  public static InputStream toInputStream(String input) {
    byte[] bytes;
    try {
      bytes = input.getBytes(DEFAULT_ENCODING);
      return new ByteArrayInputStream(bytes);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Convert the specified string to an input stream, encoded as bytes using the
   * specified character encoding.
   * <p>
   * Character encoding names can be found at <a
   * href="http://www.iana.org/assignments/character-sets">IANA</a>.
   * 
   * @param input the string to convert
   * @param encoding the encoding to use, null means platform default
   * @throws IOException if the encoding is invalid
   * @return an input stream
   */
  public static InputStream toInputStream(String input, String encoding)
      throws IOException {
    byte[] bytes =
        encoding != null ? input.getBytes(encoding) : input.getBytes(DEFAULT_ENCODING);
    return new ByteArrayInputStream(bytes);
  }

  /**
   * Compare the contents of two Streams to determine if they are equal or not.
   * <p>
   * This method buffers the input internally using
   * <code>BufferedInputStream</code> if they are not already buffered.
   * 
   * @param input1 the first stream
   * @param input2 the second stream
   * @return true if the content of the streams are equal or they both don't
   *         exist, false otherwise
   * @throws NullPointerException if either input is null
   * @throws IOException if an I/O error occurs
   */
  public static boolean contentEquals(InputStream input1, InputStream input2)
      throws IOException {
    if (!(input1 instanceof BufferedInputStream)) {
      input1 = new BufferedInputStream(input1);
    }
    if (!(input2 instanceof BufferedInputStream)) {
      input2 = new BufferedInputStream(input2);
    }

    int ch = input1.read();
    while (-1 != ch) {
      int ch2 = input2.read();
      if (ch != ch2) {
        return false;
      }
      ch = input1.read();
    }

    int ch2 = input2.read();
    return (ch2 == -1);
  }

  /**
   * Compare the contents of two Readers to determine if they are equal or not.
   * <p>
   * This method buffers the input internally using <code>BufferedReader</code>
   * if they are not already buffered.
   * 
   * @param input1 the first reader
   * @param input2 the second reader
   * @return true if the content of the readers are equal or they both don't
   *         exist, false otherwise
   * @throws NullPointerException if either input is null
   * @throws IOException if an I/O error occurs
   */
  public static boolean contentEquals(Reader input1, Reader input2)
      throws IOException {
    if (!(input1 instanceof BufferedReader)) {
      input1 = new BufferedReader(input1);
    }
    if (!(input2 instanceof BufferedReader)) {
      input2 = new BufferedReader(input2);
    }

    int ch = input1.read();
    while (-1 != ch) {
      int ch2 = input2.read();
      if (ch != ch2) {
        return false;
      }
      ch = input1.read();
    }

    int ch2 = input2.read();
    return (ch2 == -1);
  }
}
