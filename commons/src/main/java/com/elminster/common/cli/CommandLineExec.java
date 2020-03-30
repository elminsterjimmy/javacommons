package com.elminster.common.cli;

import com.elminster.common.thread.wrapper.ThreadNameWrapper;
import com.elminster.common.thread.threadpool.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * An asynchronized command line executor.
 *
 * @author jgu
 * @version 1.0
 */
public class CommandLineExec {

  /** the logger. */
  private static final Logger logger = LoggerFactory.getLogger(CommandLineExec.class);

  /** the thread pool. */
  final private ThreadPool threadPool;

  /**
   * Get the command line exec with default thread pool.
   */
  public CommandLineExec() {
    this(ThreadPool.getDefaultThreadPool());
  }

  /**
   * Constructor.
   *
   * @param threadPool
   *     the thread pool to use to execute the command.
   */
  public CommandLineExec(ThreadPool threadPool) {
    this.threadPool = threadPool;
  }

  /**
   * Execute the command to get the future.
   *
   * @param command
   *     the command to execute
   */
  public Future<CommandLineResult> execute(Command command) {
    return execute(command, new SimpleExecStreamHandler());
  }

  /**
   * Execute the command to get the future.
   *
   * @param command
   *     the command to execute
   * @param streamHandler
   *     the stream handler
   */
  public Future<CommandLineResult> execute(Command command, ExecuteStreamHandler streamHandler) {
    return threadPool.submit(() -> ThreadNameWrapper.replaceThreadName(s -> {
      ProcessBuilder pb = new ProcessBuilder();
      pb.command(command.getCommands());
      pb.directory(command.getWorkingDir());
      Map<String, String> env = pb.environment();
      env.putAll(command.getEnvironment());
      try {
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
        int exitValue = process.waitFor();
        return new CommandLineResult(exitValue, streamHandler.getStdout(), streamHandler.getStderr());
      } catch (IOException  ioe) {
        throw new RuntimeException(ioe);
      } catch (InterruptedException ie) {
        logger.warn(String.format("CLI [%s] has been interrupted.", command));
        throw new RuntimeException(ie);
      } finally {
        try {
          streamHandler.stop();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    }));
  }
}
