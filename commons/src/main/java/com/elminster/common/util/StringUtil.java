package com.elminster.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.elminster.common.constants.Constants.CharacterConstants;
import com.elminster.common.constants.Constants.StringConstants;

/**
 * Reference to Class org.apache.commons.lang.StringUtils at Apache Commons
 * Project.
 * 
 * @author Gu
 * @version 1.0
 */
public abstract class StringUtil {

  /**
   * <code>\u000a</code> linefeed LF ('\n').
   * 
   * @see <a
   *      href="http://java.sun.com/docs/books/jls/third_edition/html/lexical.html#101089">JLF:
   *      Escape Sequences for Character and String Literals</a>
   */
  public static final char LF = CharacterConstants.LF;

  /**
   * <code>\u000d</code> carriage return CR ('\r').
   * 
   * @see <a
   *      href="http://java.sun.com/docs/books/jls/third_edition/html/lexical.html#101089">JLF:
   *      Escape Sequences for Character and String Literals</a>
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
   * NOTE: This method changed in Lang version 2.0. It no longer trims the
   * String. That functionality is available in isBlank().
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
   * @return <code>true</code> if the String is not empty and not null and not
   *         whitespace
   */
  public static boolean isNotBlank(String str) {
    return !StringUtil.isBlank(str);
  }

  /**
   * <p>
   * Compares two Strings, returning <code>true</code> if they are equal.
   * </p>
   * <p>
   * <code>null</code>s are handled without exceptions. Two <code>null</code>
   * references are considered to be equal. The comparison is case sensitive.
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
   * @return <code>true</code> if the Strings are equal, case sensitive, or both
   *         <code>null</code>
   */
  public static boolean equals(String str1, String str2) {
    return str1 == null ? str2 == null : str1.equals(str2);
  }

  /**
   * <p>
   * Compares two Strings, returning <code>true</code> if they are equal
   * ignoring the case.
   * </p>
   * <p>
   * <code>null</code>s are handled without exceptions. Two <code>null</code>
   * references are considered equal. Comparison is case insensitive.
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
   * @return <code>true</code> if the Strings are equal, case insensitive, or
   *         both <code>null</code>
   */
  public static boolean equalsIgnoreCase(String str1, String str2) {
    return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
  }

  /**
   * <p>
   * Gets the leftmost <code>len</code> characters of a String.
   * </p>
   * <p>
   * If <code>len</code> characters are not available, or the String is
   * <code>null</code>, the String will be returned without an exception. An
   * exception is thrown if len is negative.
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
   * If <code>len</code> characters are not available, or the String is
   * <code>null</code>, the String will be returned without an an exception. An
   * exception is thrown if len is negative.
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
   * If <code>len</code> characters are not available, the remainder of the
   * String will be returned without an exception. If the String is
   * <code>null</code>, <code>null</code> will be returned. An exception is
   * thrown if len is negative.
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
   * Deletes all whitespaces from a String as defined by
   * {@link Character#isWhitespace(char)}.
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
   * @return the String without whitespaces, <code>null</code> if null String
   *         input
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
   * A <code>null</code> string input returns <code>null</code>. A negative
   * index is treated as zero. An index greater than the string length is
   * treated as the string length. The start index is always the smaller of the
   * two indices.
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
    return new StringBuffer(len + start - end + overlay.length() + 1)
        .append(str.substring(0, start)).append(overlay)
        .append(str.substring(end)).toString();
  }

  /**
   * <p>
   * Removes one newline from end of a String if it's there, otherwise leave it
   * alone. A newline is &quot;<code>\n</code>&quot;, &quot;<code>\r</code>
   * &quot;, or &quot;<code>\r\n</code>&quot;.
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
   * Capitalizes a String changing the first letter to title case as per
   * {@link Character#toTitleCase(char)}. No other letters are changed.
   * </p>
   * <p>
   * For a word based algorithm, see {@link WordUtils#capitalize(String)}. A
   * <code>null</code> input String returns <code>null</code>.
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
    return new StringBuffer(strLen)
        .append(Character.toTitleCase(str.charAt(0))).append(str.substring(1))
        .toString();
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
      if (CR == ch || LF == ch || CharacterConstants.SPACE == ch
          || CharacterConstants.TAB == ch) {
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
      if (CR == ch || LF == ch || CharacterConstants.SPACE == ch
          || CharacterConstants.TAB == ch) {
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
   * Convert the String to boolean. "1" = true "true" = true (ignore case) "yes"
   * = true (ignore case) "on" = true (ignore case) Others will lead to false.
   * 
   * @param string
   *          the String
   * @return the boolean value
   */
  public static boolean string2Boolean(String string) {
    return "1".equals(string) || Boolean.TRUE.toString().equalsIgnoreCase(string)
        || "yes".equalsIgnoreCase(string) || "on".equalsIgnoreCase(string);
  }

  /**
   * Return the new line in system.
   * @return the new line in system
   */
  public static String newline() {
    return System.getProperty("line.separator");
  }
}
