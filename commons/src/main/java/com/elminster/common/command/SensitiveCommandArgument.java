package com.elminster.common.command;

import com.elminster.common.constants.Constants;

/**
 * A decorator to make the command argument sensitive which shows the argument as {@literal ******}.
 *
 * @author jgu
 * @version 1.0
 */
public class SensitiveCommandArgument implements CommandArgument {

  /** the sensitive mark. */
  private static final String SENSITIVE_MARK = "******";

  private final CommandArgument commandArgument;

  public SensitiveCommandArgument(CommandArgument commandArgument) {
    this.commandArgument = commandArgument;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String[] getPlainArgument() {
    return commandArgument.getPlainArgument();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String[] getQuotedArgument() {
    return commandArgument.getQuotedArgument();
  }

  /**
   * Show the argument that set the last plain argument to {@literal ******}.
   * @return the String to show
   */
  public String toString() {
    String[] plainArgs = getPlainArgument();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < plainArgs.length - 1; i++) {
      sb.append(plainArgs[i]);
      sb.append(Constants.StringConstants.SPACE);
    }
    sb.append(SENSITIVE_MARK);
    return sb.toString();
  }
}
