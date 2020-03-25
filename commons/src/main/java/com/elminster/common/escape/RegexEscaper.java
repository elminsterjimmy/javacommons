package com.elminster.common.escape;

import static com.elminster.common.constants.RegexConstants.*;

/**
 * The Regex escaper.
 *
 * @author jgu
 * @version 1.0
 */
public class RegexEscaper implements Escaper {
  private static final Escaper INTERNAL_ESCAPER =
      EscaperBuilder.newBuilder()
          .addEscape(".", REGEX_DOT)
          .addEscape("*", REGEX_STAR)
          .addEscape("+", REGEX_PLUS)
          .addEscape("[", REGEX_LEFT_SQUARE_BRACKETS)
          .addEscape("]", REGEX_RIGHT_SQUARE_BRACKETS)
          .addEscape("(", REGEX_LEFT_PARENTHESES)
          .addEscape(")", REGEX_RIGHT_PARENTHESES)
          .addEscape("$", REGEX_DOLLAR)
          .addEscape("^", REGEX_CARET)
          .addEscape("?", REGEX_QUESTION).build();

  /**
   * {@inheritDoc}
   */
  @Override
  public String escape(String origin) {
    return INTERNAL_ESCAPER.escape(origin);
  }
}
