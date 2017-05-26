package com.elminster.common.exec;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.elminster.common.constants.Constants.StringConstants;
import com.elminster.common.misc.ThreadCreator;
import com.elminster.common.threadpool.ThreadPool;
import com.elminster.common.util.Assert;
import com.elminster.common.util.CollectionUtil;

/**
 * An asynchronized command line executor.
 * 
 * @author jgu
 * @version 1.0
 */
public class CommandLineExec {

  /** the command. */
  private final String command;
  /** the args. */
  private CommandLineArgs args;
  /** the work directory. */
  private File workingDir;
  /** the process environment. */
  private Map<String, String> environment = new HashMap<String, String>();
  /** the process builder. */
  final private ProcessBuilder pb = new ProcessBuilder();
  /** the thread creator. */
  final private ThreadCreator threadCreator = new ThreadCreator();
  /** the thread pool. */
  final private ThreadPool threadPool = ThreadPool.getDefaultThreadPool();

  /**
   * Constructor.
   */
  public CommandLineExec(String command) {
    this.command = command;
    this.workingDir = new File(".");
    threadCreator.setThreadNamePrefix(this.getClass().getSimpleName());
  }

  /**
   * Execute the command and callback when it finished or met exception.
   * @param streamHandler the stream handler
   * @param resultHandler the result handler
   */
  public void execute(final ExecuteStreamHandler streamHandler, final ExecuteResultHandler resultHandler) {
    Assert.notNull(resultHandler);
    Thread execThread = threadCreator.createThread(new Runnable() {

      @Override
      public void run() {
        try {
          pb.command(getCommand());
          pb.directory(workingDir);
          Map<String, String> env = pb.environment();
          env.putAll(environment);
          Process process = pb.start();
          if (null != streamHandler) {
            try {
              streamHandler.setProcessInputStream(process.getInputStream());
              streamHandler.setProcessOutputStream(process.getOutputStream());
              streamHandler.setProcessErrorStream(process.getErrorStream());
              streamHandler.start();
            } catch (IOException ioe) {
              process.destroy();
              throw ioe;
            }
          }
          try {
            int exitValue = process.waitFor();
            resultHandler.onFinish(exitValue);
          } catch (InterruptedException e) {
            process.destroy();
            resultHandler.onException(e);
          }
        } catch (IOException e) {
          resultHandler.onException(e);
        } finally {
          if (null != streamHandler) {
            try {
              streamHandler.stop();
            } catch (IOException e) {
              resultHandler.onException(e);
            }
          }
        }
      }

    });
    threadPool.execute(execThread);
  }
  
  /**
   * Get the command.
   * @return the command
   */
  private List<String> getCommand() {
    List<String> commands = new ArrayList<String>();
    commands.add(command);
    if (null != args) {
      List<String> nonOptionArgs = args.getNonOptionArgs();
      commands.addAll(nonOptionArgs);
      Set<String> optionArgs = args.getOptionNames();
      if (CollectionUtil.isNotEmpty(optionArgs)) {
        StringBuilder sb = new StringBuilder();
        for (String optionArg : optionArgs) {
          List<String> optionValues = args.getOptionValues(optionArg);
          if (CollectionUtil.isNotEmpty(optionValues)) {
            for (String optionValue : optionValues) {
              sb.setLength(0);
              sb.append(optionArg);
              sb.append(StringConstants.SPACE);
              sb.append(optionValue);
              commands.add(sb.toString());
            }
          }
        }
      }
    }
    return commands;
  }

  /**
   * @return the workingDir
   */
  public File getWorkingDir() {
    return workingDir;
  }

  /**
   * @param workingDir the workingDir to set
   */
  public void setWorkingDir(File workingDir) {
    this.workingDir = workingDir;
  }

  /**
   * @return the environment
   */
  public Map<String, String> getEnvironment() {
    return environment;
  }

  /**
   * @param environment the environment to set
   */
  public void setEnvironment(Map<String, String> environment) {
    this.environment = environment;
  }

  /**
   * @return the args
   */
  public CommandLineArgs getArgs() {
    return args;
  }

  /**
   * @param args the args to set
   */
  public void setArgs(CommandLineArgs args) {
    this.args = args;
  }
}
