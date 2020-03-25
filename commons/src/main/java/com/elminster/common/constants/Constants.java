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
  interface TimeUnit {
    long MILLION_SECOND = 1;
    long SECOND_IN_MS = 1000L;
    long MINUTE_IN_MS = 60 * SECOND_IN_MS;
    long HOUR_IN_MS = 60 * MINUTE_IN_MS;
    long DAY_IN_MS = 24 * HOUR_IN_MS;
    long WEEK_IN_MS = 7 * DAY_IN_MS;
  }

  /**
   * The encoding Strings.
   * 
   * @author jgu
   * @version 1.0
   */
  interface EncodingConstants {
    /** Eight-bit UCS Transformation Format. */
    String UTF8 = "UTF-8";
    /** ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1. */
    String ISO88591 = "ISO-8859-1";
    /** Seven-bit ASCII, a.k.a. ISO646-US, a.k.a. the Basic Latin block of the Unicode character set. */
    String ASCII = "US-ASCII";
    /** Sixteen-bit UCS Transformation Format, byte order identified by an optional byte-order mark. */
    String UTF16 = "UTF-16";
    /** Sixteen-bit UCS Transformation Format, big-endian byte order. */
    String UTF16BE = "UTF-16BE";
    /** Sixteen-bit UCS Transformation Format, little-endian byte order. */
    String UTF16LE = "UTF-16LE";
  }

  /**
   * The JAVA system property name.
   * 
   * @author jgu
   * @version 1.0
   */
  interface JavaSystemProperty {
    String JAVA_VERSION = "java.version";
    String JAVA_VENDOR = "java.vendor";
    String JAVA_VENDOR_URL = "java.vendor.url";
    String JAVA_HOME = "java.home";
    String JAVA_VM_SPECIFICATION_VERSION = "java.vm.specification.version";
    String JAVA_VM_SPECIFICATION_VENDOR = "java.vm.specification.vendor";
    String JAVA_VM_SPECIFICATION_NAME = "java.vm.specification.name";
    String JAVA_VM_VERSION = "java.vm.version";
    String JAVA_VM_VENDOR = "java.vm.vendor";
    String JAVA_VM_NAME = "java.vm.name";
    String JAVA_SPECIFICATION_VERSION = "java.specification.version";
    String JAVA_SPECIFICATION_VENDOR = "java.specification.vendor";
    String JAVA_SPECIFICATION_NAME = "java.specification.name";
    String JAVA_CLASS_VERSION = "java.class.version";
    String JAVA_CLASS_PATH = "java.class.path";
    String JAVA_LIBRARY_PATH = "java.library.path";
    String JAVA_IO_TMPDIR = "java.io.tmpdir";
    String JAVA_COMPILER = "java.compiler";
    String JAVA_EXT_DIR = "java.ext.dirs";
    String OS_NAME = "os.name";
    String OS_ARCH = "os.arch";
    String FILE_SEPARATOR = "file.separator";
    String PATH_SEPARATOR = "path.separator";
    String LINE_SEPARATOR = "line.separator";
    String USER_NAME = "user.name";
    String USER_HOME = "user.home";
    String USER_DIR = "user.dir";
  }

  /**
   * The character constants.
   * 
   * @author jgu
   * @version 1.0
   */
  interface CharacterConstants {
    /** <code>{</code> */
    char LEFT_BRACES = '{';
    /** <code>}</code> */
    char RIGHT_BRACES = '}';
    /** <code>[</code> */
    char LEFT_SQUARE_BRACKETS = '[';
    /** <code>]</code> */
    char RIGHT_SQUARE_BRACKETS = ']';
    /** <code>(</code> */
    char LEFT_PARENTHESES = '(';
    /** <code>)</code> */
    char RIGHT_PARENTHESES = ')';
    /** <code>_</code> */
    char UNDER_LINE = '_';
    /** <code>\n</code> */
    char LF = '\n';
    /** <code>\r</code> */
    char CR = '\r';
    /** <code>\t</code> */
    char TAB = '\t';
    /** <code>&</code> */
    char AND = '&';
    /** <code>?</code> */
    char QUESTION = '?';
    /** <code>=</code> */
    char EQUAL = '=';
    /** <code>*</code> */
    char STAR = '*';
    /** <code> </code> */
    char SPACE = ' ';
    /** <code>-</code> */
    char HYPHEN = '-';
    /** <code>,</code> */
    char COMMA = ',';
    /** <code>.</code> */
    char DOT = '.';
    /** <code>:</code> */
    char COLON = ':';
    /** <code>+</code> */
    char PLUS = '+';
    /** <code>|</code> */
    char VERTICAL_BAR = '|';
    /** <code>#</code> */
    char SHARP = '#';
    /** <code>;</code> */
    char SEMICOLON = ';';
    /** <code>/</code> */
    char SLASH = '/';
    /** <code>\</code> */
    char BACKSLASH = '\\';
    /** <code>@</code> */
    char AT = '@';
    /** <code>$</code> */
    char DOLLAR = '$';
    /** <code>%</code> */
    char PERCENT = '%';
    /** <code>'</code> */
    char QUOTE = '\'';
    /** <code>"</code> */
    char DOUBLE_QUOTE = '"';
    /** <code>^</code> */
    char CARET = '^';
    /** <code>></code> */
    char GREAT_THAN = '>';
    /** <code><</code> */
    char LESS_THAN = '<';
  }

  /**
   * The String symbols.
   * 
   * @author jgu
   * @version 1.0
   */
  interface StringConstants {
    /** <code>{</code> */
    String LEFT_BRACES = String.valueOf(CharacterConstants.LEFT_BRACES);
    /** <code>}</code> */
    String RIGHT_BRACES = String.valueOf(CharacterConstants.RIGHT_BRACES);
    /** <code>[</code> */
    String LEFT_SQUARE_BRACKETS = String.valueOf(CharacterConstants.LEFT_SQUARE_BRACKETS);
    /** <code>]</code> */
    String RIGHT_SQUARE_BRACKETS = String.valueOf(CharacterConstants.RIGHT_SQUARE_BRACKETS);
    /** <code>(</code> */
    String LEFT_PARENTHESES = String.valueOf(CharacterConstants.LEFT_PARENTHESES);
    /** <code>)</code> */
    String RIGHT_PARENTHESES = String.valueOf(CharacterConstants.RIGHT_PARENTHESES);
    /** <code>_</code> */
    String UNDER_LINE = String.valueOf(CharacterConstants.UNDER_LINE);
    /** <code>\n</code> */
    String LF = String.valueOf(CharacterConstants.LF);
    /** <code>\r</code> */
    String CR = String.valueOf(CharacterConstants.CR);
    /** <code>\t</code> */
    String TAB = String.valueOf(CharacterConstants.TAB);
    /** <code>&</code> */
    String AND = String.valueOf(CharacterConstants.AND);
    /** <code>?</code> */
    String QUESTION = String.valueOf(CharacterConstants.QUESTION);
    /** <code>=</code> */
    String EQUAL = String.valueOf(CharacterConstants.EQUAL);
    /** <code>*</code> */
    String STAR = String.valueOf(CharacterConstants.STAR);
    /** <code> </code> */
    String SPACE = String.valueOf(CharacterConstants.SPACE);
    /** <code>-</code> */
    String HYPHEN = String.valueOf(CharacterConstants.HYPHEN);
    /** <code>,</code> */
    String COMMA = String.valueOf(CharacterConstants.COMMA);
    /** <code>.</code> */
    String DOT = String.valueOf(CharacterConstants.DOT);
    /** <code>:</code> */
    String COLON = String.valueOf(CharacterConstants.COLON);
    /** <code>+</code> */
    String PLUS = String.valueOf(CharacterConstants.PLUS);
    /** <code>|</code> */
    String VERTICAL_BAR = String.valueOf(CharacterConstants.VERTICAL_BAR);
    /** <code>#</code> */
    String SHARP = String.valueOf(CharacterConstants.SHARP);
    /** <code>;</code> */
    String SEMICOLON = String.valueOf(CharacterConstants.SEMICOLON);
    /** <code>/</code> */
    String SLASH = String.valueOf(CharacterConstants.SLASH);
    /** <code>\</code> */
    String BACKSLASH = String.valueOf(CharacterConstants.BACKSLASH);
    /** <code>@</code> */
    String AT = String.valueOf(CharacterConstants.AT);
    /** <code>$</code> */
    String DOLLAR = String.valueOf(CharacterConstants.DOLLAR);
    /** <code>%</code> */
    String PERCENT = String.valueOf(CharacterConstants.PERCENT);
    /** <code>'</code> */
    String QUOTE = String.valueOf(CharacterConstants.QUOTE);
    /** <code>"</code> */
    String DOUBLE_QUOTE = String.valueOf(CharacterConstants.DOUBLE_QUOTE);
    /** <code>^</code> */
    String CARET = String.valueOf(CharacterConstants.CARET);
    /** <code>></code> */
    String GREAT_THAN = String.valueOf(CharacterConstants.GREAT_THAN);
    /** <code><</code> */
    String LESS_THAN = String.valueOf(CharacterConstants.LESS_THAN);
    /** <code></code> */
    String EMPTY_STRING = ""; //$NON-NLS-1$
    /** <code>null</code> */
    String NULL_STRING = "null"; //$NON-NLS-1$
  }
}
