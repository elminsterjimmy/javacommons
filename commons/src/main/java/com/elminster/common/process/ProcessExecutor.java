package com.elminster.common.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.elminster.common.constants.Constants.StringConstants;
import com.elminster.common.util.CollectionUtil;
import com.elminster.common.util.FileUtil;
import com.elminster.common.util.IOUtil;

/**
 * The helper class for executing command lines.
 * NOTE: NOT THREAD-SAFE!
 * 
 * @author jgu
 * @version 1.0
 */
public class ProcessExecutor {
  
  /** the command. */
  private String command;
  /** the args. */
  private List<String> args;
  /** the working directory. */
  private String workingDirectory;
  /** the output file. */
  private String outputFile;
  /** the error file. */
  private String errorFile;
  /** the process. */
  private Process process;
  /** is output closed. */
  private boolean outputClosed;
  /** is error output closed. */
  private boolean errorClosed;
  
  
  /**
   * The constructor.
   * 
   * @param command the command
   * @param args the args
   */
  public ProcessExecutor(String command, String... args) {
    this.command = command;
    if (null != args) {
      this.args = CollectionUtil.array2List(args);
    }
  }
  
  /**
   * @return the workingDirectory
   */
  public String getWorkingDirectory() {
    return workingDirectory;
  }

  /**
   * @param workingDirectory the workingDirectory to set
   */
  public void setWorkingDirectory(String workingDirectory) {
    this.workingDirectory = workingDirectory;
  }

  /**
   * @return the command
   */
  public String getCommand() {
    return command;
  }

  /**
   * @return the args
   */
  public List<String> getArgs() {
    return args;
  }

  /**
   * Execute the command.
   * @throws IOException on error
   */
  public void execute() throws IOException {
    StringBuilder totalCommand = new StringBuilder(command);
    if (CollectionUtil.isNotEmpty(args)) {
      for (String arg : args) {
        totalCommand.append(StringConstants.SPACE);
        totalCommand.append(arg);
      }
    }
    File wdir = null;
    if (null != workingDirectory) {
      boolean workingDirExist = FileUtil.isFolderExist(workingDirectory);
      if (!workingDirExist) {
        FileUtil.createFolder(workingDirectory);
      }
      wdir = new File(workingDirectory);
    }
    process = Runtime.getRuntime().exec(totalCommand.toString(), null, wdir);
    outputClosed = false;
    errorClosed = false;
  }
  
  /**
   * Get the process' exit value.
   * @return the exit value
   */
  public int getExitValue() {
    return process.exitValue();
  }
  
  /**
   * Write the output into a String.
   * @return the output String
   * @throws IOException on error
   */
  public String writeStandardOutputToString() throws IOException {
    if (outputClosed) {
      throw new IllegalStateException("Already output standard infomation.");
    }
    InputStream is = process.getInputStream();
    try {
      return IOUtil.toString(is);
    } finally {
      if (null != is) {
        is.close();
        outputClosed = true;
      }
    }
  }
  
  /**
   * Write the error output into a String.
   * @return the error output String
   * @throws IOException on error
   */
  public String writeErrorOutputToString() throws IOException {
    if (errorClosed) {
      throw new IllegalStateException("Already output error infomation.");
    }
    InputStream is = process.getErrorStream();
    try {
      return IOUtil.toString(is);
    } finally {
      if (null != is) {
        is.close();
        errorClosed = true;
      }
    }
  }
  
  /**
   * Write the output into the file.
   * @throws IOException on error
   */
  public void writeStandardOutputToFile() throws IOException {
    if (null == outputFile) {
      throw new NullPointerException("outputFile is null.");
    }
    if (outputClosed) {
      throw new IllegalStateException("Already output standard infomation.");
    }
    InputStream is = process.getInputStream();
    OutputStream out = null;
    try {
      out = new FileOutputStream(outputFile);
      IOUtil.copy(is, out);
    } finally {
      if (null != out) {
        out.close();
      }
      if (null != is) {
        is.close();
        outputClosed = true;
      }
    }
  }
  
  /**
   * Write the error output into the file.
   * @throws IOException on error
   */
  public void writeErrorOutputToFile() throws IOException {
    if (null == errorFile) {
      throw new NullPointerException("outputFile is null.");
    }
    if (errorClosed) {
      throw new IllegalStateException("Already output error infomation.");
    }
    InputStream is = process.getErrorStream();
    OutputStream out = null;
    try {
      out = new FileOutputStream(errorFile);
      IOUtil.copy(is, out);
    } finally {
      if (null != out) {
        out.close();
      }
      if (null != is) {
        is.close();
        errorClosed = true;
      }
    }
  }
}
