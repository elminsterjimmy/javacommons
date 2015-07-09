package com.elminster.common.constants;

/**
 * The regular expression constants.
 * 
 * @author jgu
 * @version 1.0
 */
public interface RegexConstants {
  /** the escaped <code>.</code>. */
	public static final String REGEX_DOT = "\\.";
	/** the escaped <code>*</code>. */
	public static final String REGEX_STAR = "\\*";
	/** the escaped <code>+</code>. */
	public static final String REGEX_PLUS = "\\+";
	/** the escaped <code>[</code>. */
	public static final String REGEX_LEFT_SQUARE_BRACKETS = "\\[";
	/** the escaped <code>]</code>. */
	public static final String REGEX_RIGHT_SQUARE_BRACKETS = "\\]";
	/** the escaped <code>(</code>. */
	public static final String REGEX_LEFT_PARENTHESES = "\\(";
	/** the escaped <code>)</code>. */
	public static final String REGEX_RIGHT_PARENTHESES = "\\)";
	/** the escaped <code>$</code>. */
	public static final String REGEX_DOLLAR = "\\$";
	/** the escaped <code>?</code>. */
	public static final String REGEX_QUESTION = "\\?";
	/** the escaped <code>^</code>. */
	public static final String REGEX_CARET = "\\^";
	/** the pattern for any included newline. */
	public static final String REGEX_ANY_INCLUDE_NEWLINE = "((.|\n)*)";
}
