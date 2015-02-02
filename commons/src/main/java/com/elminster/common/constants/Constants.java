package com.elminster.common.constants;

/**
 * The Constants.
 * 
 * @author jgu
 * @version 1.0
 */
public interface Constants {
  
  /***
   * The time unit.
   * 
   * @author jgu
   * @version 1.0
   */
  public interface TimeUnit {
    public static final long MILLION_SECOND = 1;
    public static final long SECOND_IN_MS = 1000L;
    public static final long MINUTE_IN_MS = 60 * SECOND_IN_MS;
    public static final long HOUR_IN_MS = 60 * MINUTE_IN_MS;
    public static final long DAY_IN_MS = 24 * HOUR_IN_MS;
    public static final long WEEK_IN_MS = 7 * DAY_IN_MS;
  }
  
  
  /**
   * The encoding Strings.
   * 
   * @author jgu
   * @version 1.0
   */
  public interface EncodingConstants {
    public static final String UTF8 = "UTF-8"; //$NON-NLS-1$
  }
  
  /**
   * The character constants.
   * 
   * @author jgu
   * @version 1.0
   */
  public interface CharacterConstants {
    public static final char LEFT_BRACES = '{'; 
    public static final char RIGHT_BRACES = '}'; 
    public static final char LEFT_SQUARE_BRACKETS = '['; 
    public static final char RIGHT_SQUARE_BRACKETS = ']'; 
    public static final char LEFT_PARENTHESES = '('; 
    public static final char RIGHT_PARENTHESES = ')'; 
    public static final char UNDER_LINE = '_'; 
    public static final char LF = '\n'; 
    public static final char CR = '\r'; 
    public static final char TAB = '\t';
    public static final char BACKSPACE = '\b';
    public static final char AND = '&'; 
    public static final char QUESTION = '?';
    public static final char EXCLAMATION = '!';
    public static final char TILDE = '~';
    public static final char GRAVE_ACCENT = '`';
    public static final char EQUAL = '=';
    public static final char STAR = '*'; 
    public static final char SPACE = ' '; 
    public static final char HYPHEN = '-'; 
    public static final char PLUS = '+'; 
    public static final char COMMA = ','; 
    public static final char DOT = '.'; 
    public static final char COLON = ':'; 
    public static final char VERTICAL_BAR = '|'; 
    public static final char SHARP = '#';
    public static final char SEMICOLON = ';'; 
    public static final char SLASH = '/'; 
    public static final char BACKSLASH = '\\'; 
    public static final char AT = '@';
    public static final char DOLLAR = '$';
    public static final char CARET = '^';
    public static final char QUOTE = '\'';
    public static final char DOUBLE_QUOTE = '"';
  }
  
  /**
   * The String symbols.
   * 
   * @author jgu
   * @version 1.0
   */
  public interface StringConstants {
    public static final String LEFT_BRACES = String.valueOf(CharacterConstants.LEFT_BRACES);
    public static final String RIGHT_BRACES = String.valueOf(CharacterConstants.RIGHT_BRACES);
    public static final String LEFT_SQUARE_BRACKETS = String.valueOf(CharacterConstants.LEFT_SQUARE_BRACKETS);
    public static final String RIGHT_SQUARE_BRACKETS = String.valueOf(CharacterConstants.RIGHT_SQUARE_BRACKETS);
    public static final String LEFT_PARENTHESES = String.valueOf(CharacterConstants.LEFT_PARENTHESES);
    public static final String RIGHT_PARENTHESES = String.valueOf(CharacterConstants.RIGHT_PARENTHESES);
    public static final String UNDER_LINE = String.valueOf(CharacterConstants.UNDER_LINE);
    public static final String LF = String.valueOf(CharacterConstants.LF);
    public static final String CR = String.valueOf(CharacterConstants.CR);
    public static final String TAB = String.valueOf(CharacterConstants.TAB);
    public static final String BACKSPACE = String.valueOf(CharacterConstants.BACKSPACE);
    public static final String AND = String.valueOf(CharacterConstants.AND);
    public static final String QUESTION = String.valueOf(CharacterConstants.QUESTION);
    public static final String EXCLAMATION = String.valueOf(CharacterConstants.EXCLAMATION);
    public static final String TILDE = String.valueOf(CharacterConstants.TILDE);
    public static final String GRAVE_ACCENT = String.valueOf(CharacterConstants.GRAVE_ACCENT);
    public static final String EQUAL = String.valueOf(CharacterConstants.EQUAL);
    public static final String STAR = String.valueOf(CharacterConstants.STAR);
    public static final String SPACE = String.valueOf(CharacterConstants.SPACE);
    public static final String HYPHEN = String.valueOf(CharacterConstants.HYPHEN);
    public static final String PLUS = String.valueOf(CharacterConstants.PLUS);
    public static final String COMMA = String.valueOf(CharacterConstants.COMMA);
    public static final String DOT = String.valueOf(CharacterConstants.DOT);
    public static final String COLON = String.valueOf(CharacterConstants.COLON);
    public static final String VERTICAL_BAR = String.valueOf(CharacterConstants.VERTICAL_BAR);
    public static final String SHARP = String.valueOf(CharacterConstants.SHARP);
    public static final String SEMICOLON = String.valueOf(CharacterConstants.SEMICOLON);
    public static final String SLASH = String.valueOf(CharacterConstants.SLASH);
    public static final String BACKSLASH = String.valueOf(CharacterConstants.BACKSLASH);
    public static final String AT = String.valueOf(CharacterConstants.AT);
    public static final String DOLLAR = String.valueOf(CharacterConstants.DOLLAR);
    public static final String CARET = String.valueOf(CharacterConstants.CARET);
    public static final String QUOTE = String.valueOf(CharacterConstants.QUOTE);
    public static final String DOUBLE_QUOTE = String.valueOf(CharacterConstants.DOUBLE_QUOTE);
    public static final String EMPTY_STRING = ""; //$NON-NLS-1$
    public static final String NULL_STRING = "null"; //$NON-NLS-1$
  }
}
