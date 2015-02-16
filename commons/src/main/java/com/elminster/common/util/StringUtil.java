package com.elminster.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.elminster.common.constants.Constants.CharacterConstants;
import com.elminster.common.constants.Constants.StringConstants;

/**
 * Reference to Class org.apache.commons.lang.StringUtils at Apache Commons Project.
 * 
 * @author Gu
 * @version 1.0
 */
public abstract class StringUtil {

  /**
   * <code>\u000a</code> linefeed LF ('\n').
   */
  public static final char LF = CharacterConstants.LF;

  /**
   * <code>\u000d</code> carriage return CR ('\r').
   */
  public static final char CR = CharacterConstants.CR;

  /**
   * The empty String <code>""</code>.
   */
  public static final String EMPTY = StringConstants.EMPTY_STRING;

  /**
   * Represents a failed index search.
   */
  public static final int INDEX_NOT_FOUND = -1;

  /**
   * <p>
   * The maximum size to which the padding constant(s) can expand.
   * </p>
   */
  private static final int PAD_LIMIT = 8192;

  /**
   * <p>
   * Checks if a String is empty ("") or null.
   * </p>
   * 
   * <pre>
   * StringUtils.isEmpty(null)      = true
   * StringUtils.isEmpty("")        = true
   * StringUtils.isEmpty(" ")       = false
   * StringUtils.isEmpty("bob")     = false
   * StringUtils.isEmpty("  bob  ") = false
   * </pre>
   * <p>
   * NOTE: This method changed in Lang version 2.0. It no longer trims the String. That functionality is available in
   * isBlank().
   * </p>
   * 
   * @param str
   *          the String to check, may be null
   * @return <code>true</code> if the String is empty or null
   */
  public static boolean isEmpty(String str) {
    return str == null || str.length() == 0;
  }

  /**
   * <p>
   * Checks if a String is not empty ("") and not null.
   * </p>
   * 
   * <pre>
   * StringUtils.isNotEmpty(null)      = false
   * StringUtils.isNotEmpty("")        = false
   * StringUtils.isNotEmpty(" ")       = true
   * StringUtils.isNotEmpty("bob")     = true
   * StringUtils.isNotEmpty("  bob  ") = true
   * </pre>
   * 
   * @param str
   *          the String to check, may be null
   * @return <code>true</code> if the String is not empty and not null
   */
  public static boolean isNotEmpty(String str) {
    return !StringUtil.isEmpty(str);
  }

  /**
   * <p>
   * Checks if a String is whitespace, empty ("") or null.
   * </p>
   * 
   * <pre>
   * StringUtils.isBlank(null)      = true
   * StringUtils.isBlank("")        = true
   * StringUtils.isBlank(" ")       = true
   * StringUtils.isBlank("bob")     = false
   * StringUtils.isBlank("  bob  ") = false
   * </pre>
   * 
   * @param str
   *          the String to check, may be null
   * @return <code>true</code> if the String is null, empty or whitespace
   */
  public static boolean isBlank(String str) {
    int strLen;
    if (str == null || (strLen = str.length()) == 0) {
      return true;
    }
    for (int i = 0; i < strLen; i++) {
      if ((Character.isWhitespace(str.charAt(i)) == false)) {
        return false;
      }
    }
    return true;
  }

  /**
   * <p>
   * Checks if a String is not empty (""), not null and not whitespace only.
   * </p>
   * 
   * <pre>
   * StringUtils.isNotBlank(null)      = false
   * StringUtils.isNotBlank("")        = false
   * StringUtils.isNotBlank(" ")       = false
   * StringUtils.isNotBlank("bob")     = true
   * StringUtils.isNotBlank("  bob  ") = true
   * </pre>
   * 
   * @param str
   *          the String to check, may be null
   * @return <code>true</code> if the String is not empty and not null and not whitespace
   */
  public static boolean isNotBlank(String str) {
    return !StringUtil.isBlank(str);
  }

  /**
   * <p>
   * Compares two Strings, returning <code>true</code> if they are equal.
   * </p>
   * <p>
   * <code>null</code>s are handled without exceptions. Two <code>null</code> references are considered to be equal. The
   * comparison is case sensitive.
   * </p>
   * 
   * <pre>
   * StringUtils.equals(null, null)   = true
   * StringUtils.equals(null, "abc")  = false
   * StringUtils.equals("abc", null)  = false
   * StringUtils.equals("abc", "abc") = true
   * StringUtils.equals("abc", "ABC") = false
   * </pre>
   * 
   * @see java.lang.String#equals(Object)
   * @param str1
   *          the first String, may be null
   * @param str2
   *          the second String, may be null
   * @return <code>true</code> if the Strings are equal, case sensitive, or both <code>null</code>
   */
  public static boolean equals(String str1, String str2) {
    return str1 == null ? str2 == null : str1.equals(str2);
  }

  /**
   * <p>
   * Compares two Strings, returning <code>true</code> if they are equal ignoring the case.
   * </p>
   * <p>
   * <code>null</code>s are handled without exceptions. Two <code>null</code> references are considered equal.
   * Comparison is case insensitive.
   * </p>
   * 
   * <pre>
   * StringUtils.equalsIgnoreCase(null, null)   = true
   * StringUtils.equalsIgnoreCase(null, "abc")  = false
   * StringUtils.equalsIgnoreCase("abc", null)  = false
   * StringUtils.equalsIgnoreCase("abc", "abc") = true
   * StringUtils.equalsIgnoreCase("abc", "ABC") = true
   * </pre>
   * 
   * @see java.lang.String#equalsIgnoreCase(String)
   * @param str1
   *          the first String, may be null
   * @param str2
   *          the second String, may be null
   * @return <code>true</code> if the Strings are equal, case insensitive, or both <code>null</code>
   */
  public static boolean equalsIgnoreCase(String str1, String str2) {
    return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
  }

  /**
   * <p>
   * Gets the leftmost <code>len</code> characters of a String.
   * </p>
   * <p>
   * If <code>len</code> characters are not available, or the String is <code>null</code>, the String will be returned
   * without an exception. An exception is thrown if len is negative.
   * </p>
   * 
   * <pre>
   * StringUtils.left(null, *)    = null
   * StringUtils.left(*, -ve)     = ""
   * StringUtils.left("", *)      = ""
   * StringUtils.left("abc", 0)   = ""
   * StringUtils.left("abc", 2)   = "ab"
   * StringUtils.left("abc", 4)   = "abc"
   * </pre>
   * 
   * @param str
   *          the String to get the leftmost characters from, may be null
   * @param len
   *          the length of the required String, must be zero or positive
   * @return the leftmost characters, <code>null</code> if null String input
   */
  public static String left(String str, int len) {
    if (str == null) {
      return null;
    }
    if (len < 0) {
      return EMPTY;
    }
    if (str.length() <= len) {
      return str;
    }
    return str.substring(0, len);
  }

  /**
   * <p>
   * Gets the rightmost <code>len</code> characters of a String.
   * </p>
   * <p>
   * If <code>len</code> characters are not available, or the String is <code>null</code>, the String will be returned
   * without an an exception. An exception is thrown if len is negative.
   * </p>
   * 
   * <pre>
   * StringUtils.right(null, *)    = null
   * StringUtils.right(*, -ve)     = ""
   * StringUtils.right("", *)      = ""
   * StringUtils.right("abc", 0)   = ""
   * StringUtils.right("abc", 2)   = "bc"
   * StringUtils.right("abc", 4)   = "abc"
   * </pre>
   * 
   * @param str
   *          the String to get the rightmost characters from, may be null
   * @param len
   *          the length of the required String, must be zero or positive
   * @return the rightmost characters, <code>null</code> if null String input
   */
  public static String right(String str, int len) {
    if (str == null) {
      return null;
    }
    if (len < 0) {
      return EMPTY;
    }
    if (str.length() <= len) {
      return str;
    }
    return str.substring(str.length() - len);
  }

  /**
   * <p>
   * Gets <code>len</code> characters from the middle of a String.
   * </p>
   * <p>
   * If <code>len</code> characters are not available, the remainder of the String will be returned without an
   * exception. If the String is <code>null</code>, <code>null</code> will be returned. An exception is thrown if len is
   * negative.
   * </p>
   * 
   * <pre>
   * StringUtils.mid(null, *, *)    = null
   * StringUtils.mid(*, *, -ve)     = ""
   * StringUtils.mid("", 0, *)      = ""
   * StringUtils.mid("abc", 0, 2)   = "ab"
   * StringUtils.mid("abc", 0, 4)   = "abc"
   * StringUtils.mid("abc", 2, 4)   = "c"
   * StringUtils.mid("abc", 4, 2)   = ""
   * StringUtils.mid("abc", -2, 2)  = "ab"
   * </pre>
   * 
   * @param str
   *          the String to get the characters from, may be null
   * @param pos
   *          the position to start from, negative treated as zero
   * @param len
   *          the length of the required String, must be zero or positive
   * @return the middle characters, <code>null</code> if null String input
   */
  public static String mid(String str, int pos, int len) {
    if (str == null) {
      return null;
    }
    if (len < 0 || pos > str.length()) {
      return EMPTY;
    }
    if (pos < 0) {
      pos = 0;
    }
    if (str.length() <= (pos + len)) {
      return str.substring(pos);
    }
    return str.substring(pos, pos + len);
  }

  /**
   * <p>
   * Deletes all whitespaces from a String as defined by {@link Character#isWhitespace(char)}.
   * </p>
   * 
   * <pre>
   * StringUtils.deleteWhitespace(null)         = null
   * StringUtils.deleteWhitespace("")           = ""
   * StringUtils.deleteWhitespace("abc")        = "abc"
   * StringUtils.deleteWhitespace("   ab  c  ") = "abc"
   * </pre>
   * 
   * @param str
   *          the String to delete whitespace from, may be null
   * @return the String without whitespaces, <code>null</code> if null String input
   */
  public static String deleteWhitespace(String str) {
    if (isEmpty(str)) {
      return str;
    }
    int sz = str.length();
    char[] chs = new char[sz];
    int count = 0;
    for (int i = 0; i < sz; i++) {
      if (!Character.isWhitespace(str.charAt(i))) {
        chs[count++] = str.charAt(i);
      }
    }
    if (count == sz) {
      return str;
    }
    return new String(chs, 0, count);
  }

  /**
   * <p>
   * Overlays part of a String with another String.
   * </p>
   * <p>
   * A <code>null</code> string input returns <code>null</code>. A negative index is treated as zero. An index greater
   * than the string length is treated as the string length. The start index is always the smaller of the two indices.
   * </p>
   * 
   * <pre>
   * StringUtils.overlay(null, *, *, *)            = null
   * StringUtils.overlay("", "abc", 0, 0)          = "abc"
   * StringUtils.overlay("abcdef", null, 2, 4)     = "abef"
   * StringUtils.overlay("abcdef", "", 2, 4)       = "abef"
   * StringUtils.overlay("abcdef", "", 4, 2)       = "abef"
   * StringUtils.overlay("abcdef", "zzzz", 2, 4)   = "abzzzzef"
   * StringUtils.overlay("abcdef", "zzzz", 4, 2)   = "abzzzzef"
   * StringUtils.overlay("abcdef", "zzzz", -1, 4)  = "zzzzef"
   * StringUtils.overlay("abcdef", "zzzz", 2, 8)   = "abzzzz"
   * StringUtils.overlay("abcdef", "zzzz", -2, -3) = "zzzzabcdef"
   * StringUtils.overlay("abcdef", "zzzz", 8, 10)  = "abcdefzzzz"
   * </pre>
   * 
   * @param str
   *          the String to do overlaying in, may be null
   * @param overlay
   *          the String to overlay, may be null
   * @param start
   *          the position to start overlaying at
   * @param end
   *          the position to stop overlaying before
   * @return overlayed String, <code>null</code> if null String input
   */
  public static String overlay(String str, String overlay, int start, int end) {
    if (str == null) {
      return null;
    }
    if (overlay == null) {
      overlay = EMPTY;
    }
    int len = str.length();
    if (start < 0) {
      start = 0;
    }
    if (start > len) {
      start = len;
    }
    if (end < 0) {
      end = 0;
    }
    if (end > len) {
      end = len;
    }
    if (start > end) {
      int temp = start;
      start = end;
      end = temp;
    }
    return new StringBuffer(len + start - end + overlay.length() + 1).append(str.substring(0, start)).append(overlay)
        .append(str.substring(end)).toString();
  }

  /**
   * <p>
   * Removes one newline from end of a String if it's there, otherwise leave it alone. A newline is &quot;
   * <code>\n</code>&quot;, &quot;<code>\r</code> &quot;, or &quot;<code>\r\n</code>&quot;.
   * </p>
   * <p>
   * NOTE: This method changed in 2.0. It now more closely matches Perl chomp.
   * </p>
   * 
   * <pre>
   * StringUtils.chomp(null)          = null
   * StringUtils.chomp("")            = ""
   * StringUtils.chomp("abc \r")      = "abc "
   * StringUtils.chomp("abc\n")       = "abc"
   * StringUtils.chomp("abc\r\n")     = "abc"
   * StringUtils.chomp("abc\r\n\r\n") = "abc\r\n"
   * StringUtils.chomp("abc\n\r")     = "abc\n"
   * StringUtils.chomp("abc\n\rabc")  = "abc\n\rabc"
   * StringUtils.chomp("\r")          = ""
   * StringUtils.chomp("\n")          = ""
   * StringUtils.chomp("\r\n")        = ""
   * </pre>
   * 
   * @param str
   *          the String to chomp a newline from, may be null
   * @return String without newline, <code>null</code> if null String input
   */
  public static String chomp(String str) {
    if (isEmpty(str)) {
      return str;
    }

    if (str.length() == 1) {
      char ch = str.charAt(0);
      if (ch == StringUtil.CR || ch == StringUtil.LF) {
        return EMPTY;
      }
      return str;
    }

    int lastIdx = str.length() - 1;
    char last = str.charAt(lastIdx);

    if (last == StringUtil.LF) {
      if (str.charAt(lastIdx - 1) == StringUtil.CR) {
        lastIdx--;
      }
    } else if (last != StringUtil.CR) {
      lastIdx++;
    }
    return str.substring(0, lastIdx);
  }

  /**
   * <p>
   * Capitalizes a String changing the first letter to title case as per {@link Character#toTitleCase(char)}. No other
   * letters are changed.
   * </p>
   * <p>
   * For a word based algorithm, see {@link WordUtils#capitalize(String)}. A <code>null</code> input String returns
   * <code>null</code>.
   * </p>
   * 
   * <pre>
   * StringUtils.capitalize(null)  = null
   * StringUtils.capitalize("")    = ""
   * StringUtils.capitalize("cat") = "Cat"
   * StringUtils.capitalize("cAt") = "CAt"
   * </pre>
   * 
   * @param str
   *          the String to capitalize, may be null
   * @return the capitalized String, <code>null</code> if null String input
   * @see WordUtils#capitalize(String)
   * @see #uncapitalize(String)
   * @since 2.0
   */
  public static String capitalize(String str) {
    int strLen;
    if (str == null || (strLen = str.length()) == 0) {
      return str;
    }
    return new StringBuffer(strLen).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString();
  }

  /**
   * <p>
   * Uncapitalizes a String changing the first letter to title case as per {@link Character#toTitleCase(char)}. No other
   * letters are changed.
   * </p>
   * <p>
   * For a word based algorithm, see {@link WordUtils#uncapitalize(String)}. A <code>null</code> input String returns
   * <code>null</code>.
   * </p>
   * 
   * <pre>
   * StringUtils.uncapitalize(null)  = null
   * StringUtils.uncapitalize("")    = ""
   * StringUtils.uncapitalize("Cat") = "cat"
   * StringUtils.uncapitalize("CAt") = "cAt"
   * </pre>
   * 
   * @param str
   *          the String to capitalize, may be null
   * @return the uncapitalize String, <code>null</code> if null String input
   * @see WordUtils#uncapitalize(String)
   * @see #capitalize(String)
   * @since 2.0
   */
  public static String uncapitalize(String str) {
    int strLen;
    if (str == null || (strLen = str.length()) == 0) {
      return str;
    }
    return new StringBuffer(strLen).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
  }

  /**
   * null object convert ""
   * 
   * @param object
   * @return
   */
  public static String convertNULLtoString(Object object) {
    return object == null ? EMPTY : object.toString();
  }

  /**
   * Calculated substring occurrences times
   * 
   * @param Source
   *          source string
   * @param sub
   *          sub string
   * @return number of times
   */
  public static int countSubString(String source, String sub) {
    if (StringUtil.isEmpty(source) || StringUtil.isEmpty(sub)) {
      return 0;
    }
    Pattern p = Pattern.compile(sub, Pattern.CASE_INSENSITIVE);
    Matcher m = p.matcher(source);
    int count = 0;
    while (m.find()) {
      count++;
    }
    return count;
  }

  /**
   * Remove the last spaces or tabs from specified line.
   * 
   * @param current
   *          the current line
   * @return the trimmed right line
   */
  public static String removeRightSpaceOrTabs(String current) {
    char ch;
    int whiteSpacesLen = 0;
    for (int i = current.length() - 1; i >= 0; i--) {
      ch = current.charAt(i);
      if (CR == ch || LF == ch || CharacterConstants.SPACE == ch || CharacterConstants.TAB == ch) {
        whiteSpacesLen++;
      } else {
        break;
      }
    }
    return current.substring(0, current.length() - whiteSpacesLen);
  }

  /**
   * Remove the last spaces or tabs from specified line.
   * 
   * @param current
   *          the current line
   * @return the trimmed right line
   */
  public static String removeLeftSpaceOrTabs(String current) {
    char ch;
    int whiteSpacesLen = 0;
    for (int i = 0; i < current.length(); i++) {
      ch = current.charAt(i);
      if (CR == ch || LF == ch || CharacterConstants.SPACE == ch || CharacterConstants.TAB == ch) {
        whiteSpacesLen++;
      } else {
        break;
      }
    }
    return current.substring(whiteSpacesLen + 1);
  }

  /**
   * Check two files content whether equals.
   * 
   * @param context
   *          the content
   * @param other
   *          the second content
   * @return content equals?
   */
  public static boolean contentEqualsIgnoreNewline(String content, String other) {
    if (StringUtil.isNotEmpty(content) && StringUtil.isNotEmpty(other)) {
      content = removeNewline(content);
      other = removeNewline(other);
      if (content.equals(other)) {
        return true;
      } else {
        return false;
      }
    } else if (StringUtil.isEmpty(content) && StringUtil.isEmpty(other)) {
      return true;
    }
    return false;
  }

  /**
   * Remove new lines from the content.
   * 
   * @param content
   *          the content
   * @return the content that removed new lines
   */
  private static String removeNewline(String content) {
    if (StringUtil.isNotEmpty(content)) {
      content = content.replaceAll(StringConstants.LF, EMPTY);
      content = content.replace(StringConstants.CR, EMPTY);
      return content;
    }
    return content;
  }

  /**
   * Convert the String to boolean.
   * 
   * <pre>
   * "1" = true 
   * "true" = true (ignore case)
   * "yes" = true (ignore case)
   * "on" = true (ignore case)
   * Others will lead to false.
   * </pre>
   * 
   * 
   * @param string
   *          the String
   * @return the boolean value
   */
  public static boolean string2Boolean(String string) {
    return "1".equals(string) || Boolean.TRUE.toString().equalsIgnoreCase(string) || "yes".equalsIgnoreCase(string)
        || "on".equalsIgnoreCase(string);
  }

  /**
   * <p>
   * Left pad a String with spaces (' ').
   * </p>
   *
   * <p>
   * The String is padded to the size of <code>size</code>.
   * </p>
   *
   * <pre>
   * StringUtils.leftPad(null, *)   = null
   * StringUtils.leftPad("", 3)     = "   "
   * StringUtils.leftPad("bat", 3)  = "bat"
   * StringUtils.leftPad("bat", 5)  = "  bat"
   * StringUtils.leftPad("bat", 1)  = "bat"
   * StringUtils.leftPad("bat", -1) = "bat"
   * </pre>
   *
   * @param str
   *          the String to pad out, may be null
   * @param size
   *          the size to pad to
   * @return left padded String or original String if no padding is necessary, <code>null</code> if null String input
   */
  public static String leftPad(String str, int size) {
    return leftPad(str, size, ' ');
  }

  /**
   * <p>
   * Left pad a String with a specified character.
   * </p>
   *
   * <p>
   * Pad to a size of <code>size</code>.
   * </p>
   *
   * <pre>
   * StringUtils.leftPad(null, *, *)     = null
   * StringUtils.leftPad("", 3, 'z')     = "zzz"
   * StringUtils.leftPad("bat", 3, 'z')  = "bat"
   * StringUtils.leftPad("bat", 5, 'z')  = "zzbat"
   * StringUtils.leftPad("bat", 1, 'z')  = "bat"
   * StringUtils.leftPad("bat", -1, 'z') = "bat"
   * </pre>
   *
   * @param str
   *          the String to pad out, may be null
   * @param size
   *          the size to pad to
   * @param padChar
   *          the character to pad with
   * @return left padded String or original String if no padding is necessary, <code>null</code> if null String input
   * @since 2.0
   */
  public static String leftPad(String str, int size, char padChar) {
    if (str == null) {
      return null;
    }
    int pads = size - str.length();
    if (pads <= 0) {
      return str; // returns original String when possible
    }
    if (pads > PAD_LIMIT) {
      return leftPad(str, size, String.valueOf(padChar));
    }
    return padding(pads, padChar).concat(str);
  }

  /**
   * <p>
   * Left pad a String with a specified String.
   * </p>
   *
   * <p>
   * Pad to a size of <code>size</code>.
   * </p>
   *
   * <pre>
   * StringUtils.leftPad(null, *, *)      = null
   * StringUtils.leftPad("", 3, "z")      = "zzz"
   * StringUtils.leftPad("bat", 3, "yz")  = "bat"
   * StringUtils.leftPad("bat", 5, "yz")  = "yzbat"
   * StringUtils.leftPad("bat", 8, "yz")  = "yzyzybat"
   * StringUtils.leftPad("bat", 1, "yz")  = "bat"
   * StringUtils.leftPad("bat", -1, "yz") = "bat"
   * StringUtils.leftPad("bat", 5, null)  = "  bat"
   * StringUtils.leftPad("bat", 5, "")    = "  bat"
   * </pre>
   *
   * @param str
   *          the String to pad out, may be null
   * @param size
   *          the size to pad to
   * @param padStr
   *          the String to pad with, null or empty treated as single space
   * @return left padded String or original String if no padding is necessary, <code>null</code> if null String input
   */
  public static String leftPad(String str, int size, String padStr) {
    if (str == null) {
      return null;
    }
    if (isEmpty(padStr)) {
      padStr = " ";
    }
    int padLen = padStr.length();
    int strLen = str.length();
    int pads = size - strLen;
    if (pads <= 0) {
      return str; // returns original String when possible
    }
    if (padLen == 1 && pads <= PAD_LIMIT) {
      return leftPad(str, size, padStr.charAt(0));
    }

    if (pads == padLen) {
      return padStr.concat(str);
    } else if (pads < padLen) {
      return padStr.substring(0, pads).concat(str);
    } else {
      char[] padding = new char[pads];
      char[] padChars = padStr.toCharArray();
      for (int i = 0; i < pads; i++) {
        padding[i] = padChars[i % padLen];
      }
      return new String(padding).concat(str);
    }
  }

  /**
   * <p>
   * Right pad a String with spaces (' ').
   * </p>
   *
   * <p>
   * The String is padded to the size of <code>size</code>.
   * </p>
   *
   * <pre>
   * StringUtils.rightPad(null, *)   = null
   * StringUtils.rightPad("", 3)     = "   "
   * StringUtils.rightPad("bat", 3)  = "bat"
   * StringUtils.rightPad("bat", 5)  = "bat  "
   * StringUtils.rightPad("bat", 1)  = "bat"
   * StringUtils.rightPad("bat", -1) = "bat"
   * </pre>
   *
   * @param str
   *          the String to pad out, may be null
   * @param size
   *          the size to pad to
   * @return right padded String or original String if no padding is necessary, <code>null</code> if null String input
   */
  public static String rightPad(String str, int size) {
    return rightPad(str, size, ' ');
  }

  /**
   * <p>
   * Right pad a String with a specified character.
   * </p>
   *
   * <p>
   * The String is padded to the size of <code>size</code>.
   * </p>
   *
   * <pre>
   * StringUtils.rightPad(null, *, *)     = null
   * StringUtils.rightPad("", 3, 'z')     = "zzz"
   * StringUtils.rightPad("bat", 3, 'z')  = "bat"
   * StringUtils.rightPad("bat", 5, 'z')  = "batzz"
   * StringUtils.rightPad("bat", 1, 'z')  = "bat"
   * StringUtils.rightPad("bat", -1, 'z') = "bat"
   * </pre>
   *
   * @param str
   *          the String to pad out, may be null
   * @param size
   *          the size to pad to
   * @param padChar
   *          the character to pad with
   * @return right padded String or original String if no padding is necessary, <code>null</code> if null String input
   * @since 2.0
   */
  public static String rightPad(String str, int size, char padChar) {
    if (str == null) {
      return null;
    }
    int pads = size - str.length();
    if (pads <= 0) {
      return str; // returns original String when possible
    }
    if (pads > PAD_LIMIT) {
      return rightPad(str, size, String.valueOf(padChar));
    }
    return str.concat(padding(pads, padChar));
  }

  /**
   * <p>
   * Right pad a String with a specified String.
   * </p>
   *
   * <p>
   * The String is padded to the size of <code>size</code>.
   * </p>
   *
   * <pre>
   * StringUtils.rightPad(null, *, *)      = null
   * StringUtils.rightPad("", 3, "z")      = "zzz"
   * StringUtils.rightPad("bat", 3, "yz")  = "bat"
   * StringUtils.rightPad("bat", 5, "yz")  = "batyz"
   * StringUtils.rightPad("bat", 8, "yz")  = "batyzyzy"
   * StringUtils.rightPad("bat", 1, "yz")  = "bat"
   * StringUtils.rightPad("bat", -1, "yz") = "bat"
   * StringUtils.rightPad("bat", 5, null)  = "bat  "
   * StringUtils.rightPad("bat", 5, "")    = "bat  "
   * </pre>
   *
   * @param str
   *          the String to pad out, may be null
   * @param size
   *          the size to pad to
   * @param padStr
   *          the String to pad with, null or empty treated as single space
   * @return right padded String or original String if no padding is necessary, <code>null</code> if null String input
   */
  public static String rightPad(String str, int size, String padStr) {
    if (str == null) {
      return null;
    }
    if (isEmpty(padStr)) {
      padStr = " ";
    }
    int padLen = padStr.length();
    int strLen = str.length();
    int pads = size - strLen;
    if (pads <= 0) {
      return str; // returns original String when possible
    }
    if (padLen == 1 && pads <= PAD_LIMIT) {
      return rightPad(str, size, padStr.charAt(0));
    }

    if (pads == padLen) {
      return str.concat(padStr);
    } else if (pads < padLen) {
      return str.concat(padStr.substring(0, pads));
    } else {
      char[] padding = new char[pads];
      char[] padChars = padStr.toCharArray();
      for (int i = 0; i < pads; i++) {
        padding[i] = padChars[i % padLen];
      }
      return str.concat(new String(padding));
    }
  }

  /**
   * <p>
   * Returns padding using the specified delimiter repeated to a given length.
   * </p>
   *
   * <pre>
   * StringUtils.padding(0, 'e')  = ""
   * StringUtils.padding(3, 'e')  = "eee"
   * StringUtils.padding(-2, 'e') = IndexOutOfBoundsException
   * </pre>
   *
   * <p>
   * Note: this method doesn't not support padding with <a
   * href="http://www.unicode.org/glossary/#supplementary_character">Unicode Supplementary Characters</a> as they
   * require a pair of <code>char</code>s to be represented. If you are needing to support full I18N of your
   * applications consider using {@link #repeat(String, int)} instead.
   * </p>
   *
   * @param repeat
   *          number of times to repeat delim
   * @param padChar
   *          character to repeat
   * @return String with repeated character
   * @throws IndexOutOfBoundsException
   *           if <code>repeat &lt; 0</code>
   * @see #repeat(String, int)
   */
  private static String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
    if (repeat < 0) {
      throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
    }
    final char[] buf = new char[repeat];
    for (int i = 0; i < buf.length; i++) {
      buf[i] = padChar;
    }
    return new String(buf);
  }

  /**
   * <p>
   * Checks whether the String a valid Java number.
   * </p>
   *
   * <p>
   * Valid numbers include hexadecimal marked with the <code>0x</code> qualifier, scientific notation and numbers marked
   * with a type qualifier (e.g. 123L).
   * </p>
   *
   * <p>
   * <code>Null</code> and empty String will return <code>false</code>.
   * </p>
   *
   * @param str
   *          the <code>String</code> to check
   * @return <code>true</code> if the string is a correctly formatted number
   */
  public static boolean isNumber(String str) {
    if (StringUtil.isEmpty(str)) {
      return false;
    }
    char[] chars = str.toCharArray();
    int sz = chars.length;
    boolean hasExp = false;
    boolean hasDecPoint = false;
    boolean allowSigns = false;
    boolean foundDigit = false;
    // deal with any possible sign up front
    int start = (chars[0] == '-') ? 1 : 0;
    if (sz > start + 1) {
      if (chars[start] == '0' && chars[start + 1] == 'x') {
        int i = start + 2;
        if (i == sz) {
          return false; // str == "0x"
        }
        // checking hex (it can't be anything else)
        for (; i < chars.length; i++) {
          if ((chars[i] < '0' || chars[i] > '9') && (chars[i] < 'a' || chars[i] > 'f')
              && (chars[i] < 'A' || chars[i] > 'F')) {
            return false;
          }
        }
        return true;
      }
    }
    sz--; // don't want to loop to the last char, check it afterwords
          // for type qualifiers
    int i = start;
    // loop to the next to last char or to the last char if we need another digit to
    // make a valid number (e.g. chars[0..5] = "1234E")
    while (i < sz || (i < sz + 1 && allowSigns && !foundDigit)) {
      if (chars[i] >= '0' && chars[i] <= '9') {
        foundDigit = true;
        allowSigns = false;

      } else if (chars[i] == '.') {
        if (hasDecPoint || hasExp) {
          // two decimal points or dec in exponent
          return false;
        }
        hasDecPoint = true;
      } else if (chars[i] == 'e' || chars[i] == 'E') {
        // we've already taken care of hex.
        if (hasExp) {
          // two E's
          return false;
        }
        if (!foundDigit) {
          return false;
        }
        hasExp = true;
        allowSigns = true;
      } else if (chars[i] == '+' || chars[i] == '-') {
        if (!allowSigns) {
          return false;
        }
        allowSigns = false;
        foundDigit = false; // we need a digit after the E
      } else {
        return false;
      }
      i++;
    }
    if (i < chars.length) {
      if (chars[i] >= '0' && chars[i] <= '9') {
        // no type qualifier, OK
        return true;
      }
      if (chars[i] == 'e' || chars[i] == 'E') {
        // can't have an E at the last byte
        return false;
      }
      if (chars[i] == '.') {
        if (hasDecPoint || hasExp) {
          // two decimal points or dec in exponent
          return false;
        }
        // single trailing decimal point after non-exponent is ok
        return foundDigit;
      }
      if (!allowSigns && (chars[i] == 'd' || chars[i] == 'D' || chars[i] == 'f' || chars[i] == 'F')) {
        return foundDigit;
      }
      if (chars[i] == 'l' || chars[i] == 'L') {
        // not allowing L with an exponent
        return foundDigit && !hasExp;
      }
      // last character is illegal
      return false;
    }
    // allowSigns is true iff the val ends in 'E'
    // found digit it to make sure weird stuff like '.' and '1E-' doesn't pass
    return !allowSigns && foundDigit;
  }

  /**
   * <p>
   * Checks whether the <code>String</code> contains only digit characters.
   * </p>
   *
   * <p>
   * <code>Null</code> and empty String will return <code>false</code>.
   * </p>
   *
   * @param str
   *          the <code>String</code> to check
   * @return <code>true</code> if str contains only unicode numeric
   */
  public static boolean isDigits(String str) {
    if (StringUtil.isEmpty(str)) {
      return false;
    }
    for (int i = 0; i < str.length(); i++) {
      if (!Character.isDigit(str.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Return the new line in system.
   * 
   * @return the new line in system
   */
  public static String newline() {
    return System.getProperty("line.separator");
  }
}
