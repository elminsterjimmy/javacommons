package com.elminster.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regex Utilities.
 *
 * @author jgu
 * @version 1.0
 */
abstract public class RegexUtil {

  /**
   * Check if the String matches the regex.
   *
   * @param regex
   *     the regex
   * @param str2check
   *     the String to check
   * @return if the String matches the regex
   */
  public static boolean matches(String str2check, String regex) {
    Assert.notNull(regex);
    Assert.notNull(str2check);
    Pattern pattern = Pattern.compile(regex);
    return matches(str2check, pattern);
  }

  /**
   * Check if the String matches the regex pattern.
   *
   * @param pattern
   *     the regex pattern
   * @param str2check
   *     the String to check
   * @return if the String matches the regex
   */
  public static boolean matches(String str2check, Pattern pattern) {
    Assert.notNull(pattern);
    Assert.notNull(str2check);
    Matcher matcher = pattern.matcher(str2check);
    return matcher.matches();
  }

  /**
   * Get the all groups in the String with the regex.
   *
   * @param str2check
   *     the String to check
   * @param regex
   *     the regex
   * @return the all groups in the String with the regex pattern
   */
  public static String[] getAllGroups(String str2check, String regex) {
    Assert.notNull(str2check);
    Assert.notNull(regex);
    Pattern pattern = Pattern.compile(regex);
    return getAllGroups(str2check, pattern);
  }

  /**
   * Get the all groups in the String with the regex pattern.
   *
   * @param str2check
   *     the String to check
   * @param pattern
   *     the regex pattern
   * @return the all groups in the String with the regex pattern
   */
  public static String[] getAllGroups(String str2check, Pattern pattern) {
    Assert.notNull(pattern);
    Assert.notNull(str2check);
    Matcher matcher = pattern.matcher(str2check);
    if (matcher.matches()) {
      int groupCount = matcher.groupCount();
      String[] groups = new String[groupCount];
      for (int i = 0; i < groupCount; i++) {
        groups[i] = matcher.group(i);
      }
      return groups;
    }
    return null;
  }
}
