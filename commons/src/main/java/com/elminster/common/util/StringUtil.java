package com.elminster.common.util;

import com.elminster.common.constants.Constants.CharacterConstants;
import com.elminster.common.constants.Constants.JavaSystemProperty;
import com.elminster.common.constants.Constants.StringConstants;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

  /** the default pad <code> </code>*/
  private static final char DEFAULT_PAD_CHAR = CharacterConstants.SPACE;
  
  /** the default pad <code> </code>*/
  private static final String DEFAULT_PAD_STRING = StringConstants.SPACE;

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
    return StringUtils.isEmpty(str);
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
    return StringUtils.isBlank(str);
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
    return StringUtils.equals(str1, str2);
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
    return StringUtils.equalsIgnoreCase(str1, str2);
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
    return StringUtils.left(str, len);
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
    return StringUtils.right(str, len);
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
    return StringUtils.mid(str, pos, len);
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
    return StringUtils.chomp(str);
  }

  /**
   * <p>
   * Capitalizes a String changing the first letter to title case as per {@link Character#toTitleCase(char)}. No other
   * letters are changed.
   * </p>
   * <p>
   * A <code>null</code> input String returns
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
   */
  public static String capitalize(String str) {
    return StringUtils.capitalize(str);
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
   * @param source
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
    return current.substring(whiteSpacesLen);
  }

  /**
   * Check two files content whether equals.
   * 
   * @param content
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
    return "1".equals(string)
            || Boolean.TRUE.toString().equalsIgnoreCase(string)
            || "yes".equalsIgnoreCase(string)
            || "on".equalsIgnoreCase(string);
  }
  
  /**
   * Replace last matched string (No Regex).
   * 
   * @param string
   *          the origin String
   * @param toReplace
   *          the String to replace
   * @param replacement
   *          the replacement
   * @return the replaced String
   */
  public static String replaceLast(String string, String toReplace, String replacement) {
    int pos = string.lastIndexOf(toReplace);
    if (pos > -1) {
      return new StringBuilder(string.substring(0, pos)).append(replacement)
          .append(string.substring(pos + toReplace.length())).toString();
    } else {
      return string;
    }
  }
  
  /**
   * Replace string by the indexes (No Regex).
   * 
   * @param string
   *          the origin String
   * @param replaceStart
   *          where start to replace
   * @param replaceEnd
   *          where end to replace
   * @param replacement
   *          the replacement
   * @return the replaced String
   */
  public static String replaceByIndexes(String string, int replaceStart, int replaceEnd, String replacement) {
    return new StringBuilder(string.substring(0, replaceStart)).append(replacement)
          .append(string.substring(replaceEnd)).toString();
  }

  /**
   * Return the new line in system.
   * 
   * @return the new line in system
   */
  public static String newline() {
    return System.getProperty(JavaSystemProperty.LINE_SEPARATOR);
  }
  
  /**
   * Remove all newlines and space from leading or tailing in the String.
   * @param str the String
   * @return chompTrimed String
   */
  public static String chompTrim(String str) {
    String rtn = str;
    if (null != rtn) {
      int i = 0;
      int j = str.length() - 1;
      while (i < j) {
        char head = rtn.charAt(i);
        char tail = rtn.charAt(j);
        if (CharacterConstants.SPACE == head || CharacterConstants.LF == head
            || CharacterConstants.CR == head || CharacterConstants.TAB == head) {
          i++;
        } else {
          if (CharacterConstants.SPACE == tail || CharacterConstants.LF == tail
              || CharacterConstants.CR == tail || CharacterConstants.TAB == tail) {
            j--;
          } else {
            break;
          }
        }
      }
      rtn = rtn.substring(i, j + 1);
    }
    return rtn;
  }
}
