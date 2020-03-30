package com.elminster.common.cli;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CommandLineResult {

  final private int exitCode;
  private String stdout;
  private String stderr;

  public CommandLineResult(final int exitCode, final String stdout, final String stderr) {
    this.exitCode = exitCode;
    this.stderr = stderr;
    this.stdout = stdout;
  }

  public int getExitCode() {
    return exitCode;
  }

  public String getStdout() {
    return stdout;
  }

  public String getStderr() {
    return stderr;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
