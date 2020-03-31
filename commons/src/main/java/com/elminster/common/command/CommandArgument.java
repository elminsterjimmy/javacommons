package com.elminster.common.command;

/**
 * The command argument.
 *
 * @author jgu
 * @version 1.0
 */
public interface CommandArgument {

  /**
   * Get the plain argument.
   * @return the plain argument
   */
  String[] getPlainArgument();

  /**
   * Get the quoted argument around with {@literal "} to avoid special characters in it.
   * @return the quoted argument around with {@literal "}
   */
  String[] getQuotedArgument();
}
