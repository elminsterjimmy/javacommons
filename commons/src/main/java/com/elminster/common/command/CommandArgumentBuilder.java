package com.elminster.common.command;

/**
 * The Command Argument builder.
 *
 * @author jgu
 * @version 1.0
 */
public class CommandArgumentBuilder {

  public static CommandArgument create(String argument) {
    return create(argument, false);
  }

  public static CommandArgument create(String argument, boolean sensitive) {
    CommandArgument commandArgument = new SimpleCommandArgument(argument);
    if (sensitive) {
      commandArgument = new SensitiveCommandArgument(commandArgument);
    }
    return commandArgument;
  }

  public static CommandArgument createFlagged(String flagged, String argument) {
    return createFlagged(flagged, argument, false);
  }

  public static CommandArgument createFlagged(String flagged, String argument, boolean sensitive) {
    CommandArgument commandArgument = new FlaggedCommandArgument(flagged, argument);
    if (sensitive) {
      commandArgument = new SensitiveCommandArgument(commandArgument);
    }
    return commandArgument;
  }
}
