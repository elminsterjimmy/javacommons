package com.elminster.common.command;

import com.elminster.common.constants.Constants;
import com.elminster.common.util.Assert;
import org.apache.commons.text.translate.*;

import java.util.HashMap;

/**
 * The simple command argument.
 *
 * @author jgu
 * @version 1.0
 */
public class SimpleCommandArgument implements CommandArgument {

  public final static CharSequenceTranslator DOUBLE_QUOTE_ESCAPER =
      new AggregateTranslator(
          new LookupTranslator(
              new HashMap<CharSequence, CharSequence>() {{
                put("\"", "\\\"");
                put("\\", "\\\\");
                put("$", "\\$");
                put("`", "\\`");
              }}
          ),
          new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE),
          UnicodeEscaper.outsideOf(32, 0x7f) // don't escapeDoubleQuoteString Java control characters in this range.
      );

  /** the argument. */
  private final String argument;

  public SimpleCommandArgument(String argument) {
    Assert.notNull(argument);
    this.argument = argument;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String[] getPlainArgument() {
    return new String[]{argument};
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String[] getQuotedArgument() {
    return new String[]{new StringBuilder(argument.length() + 32)
        .append(Constants.StringConstants.DOUBLE_QUOTE)
        .append(DOUBLE_QUOTE_ESCAPER.translate(argument))
        .append(Constants.StringConstants.DOUBLE_QUOTE)
        .toString()};
  }

  /**
   * {@inheritDoc}
   */
  public String toString() {
    return argument;
  }
}
