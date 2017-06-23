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
    /** Eight-bit UCS Transformation Format. */
    public static final String UTF8 = "UTF-8";
    /** ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1. */
    public static final String ISO88591 = "ISO-8859-1";
    /** Seven-bit ASCII, a.k.a. ISO646-US, a.k.a. the Basic Latin block of the Unicode character set. */
    public static final String ASCII = "US-ASCII";
    /** Sixteen-bit UCS Transformation Format, byte order identified by an optional byte-order mark. */
    public static final String UTF16 = "UTF-16";
    /** Sixteen-bit UCS Transformation Format, big-endian byte order. */
    public static final String UTF16BE = "UTF-16BE";
    /** Sixteen-bit UCS Transformation Format, little-endian byte order. */
    public static final String UTF16LE = "UTF-16LE";
  }

  /**
   * The JAVA system property name.
   * 
   * @author jgu
   * @version 1.0
   */
  public interface JavaSystemProperty {
    public static final String JAVA_VERSION = "java.version";
    public static final String JAVA_VENDOR = "java.vendor";
    public static final String JAVA_VENDOR_URL = "java.vendor.url";
    public static final String JAVA_HOME = "java.home";
    public static final String JAVA_VM_SPECIFICATION_VERSION = "java.vm.specification.version";
    public static final String JAVA_VM_SPECIFICATION_VENDOR = "java.vm.specification.vendor";
    public static final String JAVA_VM_SPECIFICATION_NAME = "java.vm.specification.name";
    public static final String JAVA_VM_VERSION = "java.vm.version";
    public static final String JAVA_VM_VENDOR = "java.vm.vendor";
    public static final String JAVA_VM_NAME = "java.vm.name";
    public static final String JAVA_SPECIFICATION_VERSION = "java.specification.version";
    public static final String JAVA_SPECIFICATION_VENDOR = "java.specification.vendor";
    public static final String JAVA_SPECIFICATION_NAME = "java.specification.name";
    public static final String JAVA_CLASS_VERSION = "java.class.version";
    public static final String JAVA_CLASS_PATH = "java.class.path";
    public static final String JAVA_LIBRARY_PATH = "java.library.path";
    public static final String JAVA_IO_TMPDIR = "java.io.tmpdir";
    public static final String JAVA_COMPILER = "java.compiler";
    public static final String JAVA_EXT_DIR = "java.ext.dirs";
    public static final String OS_NAME = "os.name";
    public static final String OS_ARCH = "os.arch";
    public static final String FILE_SEPARATOR = "file.separator";
    public static final String PATH_SEPARATOR = "path.separator";
    public static final String LINE_SEPARATOR = "line.separator";
    public static final String USER_NAME = "user.name";
    public static final String USER_HOME = "user.home";
    public static final String USER_DIR = "user.dir";
  }

  /**
   * The character constants.
   * 
   * @author jgu
   * @version 1.0
   */
  public interface CharacterConstants {
    /** <code>{</code> */
    public static final char LEFT_BRACES = '{';
    /** <code>}</code> */
    public static final char RIGHT_BRACES = '}';
    /** <code>[</code> */
    public static final char LEFT_SQUARE_BRACKETS = '[';
    /** <code>]</code> */
    public static final char RIGHT_SQUARE_BRACKETS = ']';
    /** <code>(</code> */
    public static final char LEFT_PARENTHESES = '(';
    /** <code>)</code> */
    public static final char RIGHT_PARENTHESES = ')';
    /** <code>_</code> */
    public static final char UNDER_LINE = '_';
    /** <code>\n</code> */
    public static final char LF = '\n';
    /** <code>\r</code> */
    public static final char CR = '\r';
    /** <code>\t</code> */
    public static final char TAB = '\t';
    /** <code>&</code> */
    public static final char AND = '&';
    /** <code>?</code> */
    public static final char QUESTION = '?';
    /** <code>=</code> */
    public static final char EQUAL = '=';
    /** <code>*</code> */
    public static final char STAR = '*';
    /** <code> </code> */
    public static final char SPACE = ' ';
    /** <code>-</code> */
    public static final char HYPHEN = '-';
    /** <code>,</code> */
    public static final char COMMA = ',';
    /** <code>.</code> */
    public static final char DOT = '.';
    /** <code>:</code> */
    public static final char COLON = ':';
    /** <code>+</code> */
    public static final char PLUS = '+';
    /** <code>|</code> */
    public static final char VERTICAL_BAR = '|';
    /** <code>#</code> */
    public static final char SHARP = '#';
    /** <code>;</code> */
    public static final char SEMICOLON = ';';
    /** <code>/</code> */
    public static final char SLASH = '/';
    /** <code>\</code> */
    public static final char BACKSLASH = '\\';
    /** <code>@</code> */
    public static final char AT = '@';
    /** <code>$</code> */
    public static final char DOLLAR = '$';
    /** <code>%</code> */
    public static final char PERCENT = '%';
    /** <code>'</code> */
    public static final char QUOTE = '\'';
    /** <code>"</code> */
    public static final char DOUBLE_QUOTE = '"';
    /** <code>^</code> */
    public static final char CARET = '^';
    /** <code>></code> */
    public static final char GREAT_THAN = '>';
    /** <code><</code> */
    public static final char LESS_THAN = '<';
  }

  /**
   * The String symbols.
   * 
   * @author jgu
   * @version 1.0
   */
  public interface StringConstants {
    /** <code>{</code> */
    public static final String LEFT_BRACES = String.valueOf(CharacterConstants.LEFT_BRACES);
    /** <code>}</code> */
    public static final String RIGHT_BRACES = String.valueOf(CharacterConstants.RIGHT_BRACES);
    /** <code>[</code> */
    public static final String LEFT_SQUARE_BRACKETS = String.valueOf(CharacterConstants.LEFT_SQUARE_BRACKETS);
    /** <code>]</code> */
    public static final String RIGHT_SQUARE_BRACKETS = String.valueOf(CharacterConstants.RIGHT_SQUARE_BRACKETS);
    /** <code>(</code> */
    public static final String LEFT_PARENTHESES = String.valueOf(CharacterConstants.LEFT_PARENTHESES);
    /** <code>)</code> */
    public static final String RIGHT_PARENTHESES = String.valueOf(CharacterConstants.RIGHT_PARENTHESES);
    /** <code>_</code> */
    public static final String UNDER_LINE = String.valueOf(CharacterConstants.UNDER_LINE);
    /** <code>\n</code> */
    public static final String LF = String.valueOf(CharacterConstants.LF);
    /** <code>\r</code> */
    public static final String CR = String.valueOf(CharacterConstants.CR);
    /** <code>\t</code> */
    public static final String TAB = String.valueOf(CharacterConstants.TAB);
    /** <code>&</code> */
    public static final String AND = String.valueOf(CharacterConstants.AND);
    /** <code>?</code> */
    public static final String QUESTION = String.valueOf(CharacterConstants.QUESTION);
    /** <code>=</code> */
    public static final String EQUAL = String.valueOf(CharacterConstants.EQUAL);
    /** <code>*</code> */
    public static final String STAR = String.valueOf(CharacterConstants.STAR);
    /** <code> </code> */
    public static final String SPACE = String.valueOf(CharacterConstants.SPACE);
    /** <code>-</code> */
    public static final String HYPHEN = String.valueOf(CharacterConstants.HYPHEN);
    /** <code>,</code> */
    public static final String COMMA = String.valueOf(CharacterConstants.COMMA);
    /** <code>.</code> */
    public static final String DOT = String.valueOf(CharacterConstants.DOT);
    /** <code>:</code> */
    public static final String COLON = String.valueOf(CharacterConstants.COLON);
    /** <code>+</code> */
    public static final String PLUS = String.valueOf(CharacterConstants.PLUS);
    /** <code>|</code> */
    public static final String VERTICAL_BAR = String.valueOf(CharacterConstants.VERTICAL_BAR);
    /** <code>#</code> */
    public static final String SHARP = String.valueOf(CharacterConstants.SHARP);
    /** <code>;</code> */
    public static final String SEMICOLON = String.valueOf(CharacterConstants.SEMICOLON);
    /** <code>/</code> */
    public static final String SLASH = String.valueOf(CharacterConstants.SLASH);
    /** <code>\</code> */
    public static final String BACKSLASH = String.valueOf(CharacterConstants.BACKSLASH);
    /** <code>@</code> */
    public static final String AT = String.valueOf(CharacterConstants.AT);
    /** <code>$</code> */
    public static final String DOLLAR = String.valueOf(CharacterConstants.DOLLAR);
    /** <code>%</code> */
    public static final String PERCENT = String.valueOf(CharacterConstants.PERCENT);
    /** <code>'</code> */
    public static final String QUOTE = String.valueOf(CharacterConstants.QUOTE);
    /** <code>"</code> */
    public static final String DOUBLE_QUOTE = String.valueOf(CharacterConstants.DOUBLE_QUOTE);
    /** <code>^</code> */
    public static final String CARET = String.valueOf(CharacterConstants.CARET);
    /** <code>></code> */
    public static final String GREAT_THAN = String.valueOf(CharacterConstants.GREAT_THAN);
    /** <code><</code> */
    public static final String LESS_THAN = String.valueOf(CharacterConstants.LESS_THAN);
    /** <code></code> */
    public static final String EMPTY_STRING = ""; //$NON-NLS-1$
    /** <code>null</code> */
    public static final String NULL_STRING = "null"; //$NON-NLS-1$
  }
}
