package com.elminster.common.cli;

import com.elminster.common.constants.Constants;
import com.elminster.common.util.Assert;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.File;
import java.util.Collections;
import java.util.Map;

public class Command {

  private static final File PWD = new File(".");
  private static final Map<String, String> EMPTY_ENV = Collections.emptyMap();

  /** the command. */
  private final String command;
  /** the work directory. */
  private final File workingDir;
  /** the process environment. */
  private final Map<String, String> environment;

  public Command(String command) {
    this(command, PWD, EMPTY_ENV);
  }

  public Command(String command, File workingDir, Map<String, String> environment) {
    Assert.notBlank(command);
    this.command = command;
    this.workingDir = workingDir;
    this.environment = environment;
  }

  public String getCommandName() {
    return command.split(Constants.StringConstants.SPACE)[0];
  }

  /**
   * Fixme: handle space in arg
   * @return
   */
  public String[] getCommands() {
    return command.split(Constants.StringConstants.SPACE);
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
