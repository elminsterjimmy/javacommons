package com.elminster.common.command;

import com.elminster.common.util.Assert;
import com.elminster.common.util.CollectionUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Command {

  private static final File PWD = new File(".");
  private static final Map<String, String> EMPTY_ENV = Collections.emptyMap();

  /** the command. */
  private final String command;
  /** the work directory. */
  private final File workingDir;
  /** the command args. */
  private final CommandArgument[] args;
  /** the process environment. */
  private final Map<String, String> environment;

  public Command(String command, CommandArgument... args) {
    this(command, args, PWD, EMPTY_ENV);
  }

  public Command(String command, CommandArgument[] args, File workingDir, Map<String, String> environment) {
    Assert.notBlank(command);
    this.command = command;
    this.args = args;
    this.workingDir = workingDir;
    this.environment = environment;
  }

  public String getCommandName() {
    return command;
  }

  /**
   * Get the commands to run.
   * @return commands
   */
  public String[] getCommands() {
    int len = null == args ? 1 : 1 + args.length;
    List<String> cmds = new ArrayList<>(len);
    cmds.add(command);
    for (CommandArgument arg : args) {
      String[] quotedArgs = arg.getQuotedArgument();
      for (String quotedArg : quotedArgs) {
        cmds.add(quotedArg);
      }
    }
    return (String[]) CollectionUtil.collection2Array(cmds);
  }

  public File getWorkingDir() {
    return workingDir;
  }

  public Map<String, String> getEnvironment() {
    return environment;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
