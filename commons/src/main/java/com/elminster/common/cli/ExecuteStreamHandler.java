package com.elminster.common.cli;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Redirct the execute streams.
 * This interface is intend to interact with the command line.
 *
 * @author jgu
 * @version 1.0
 */
public interface ExecuteStreamHandler {

  /**
   * set the process input stream.
   *
   * @param inputStream
   *     the inputstream
   * @throws IOException
   *     on error
   */
  void setProcessInputStream(InputStream inputStream) throws IOException;

  /**
   * set the process out stream.
   *
   * @param outputStream
   *     the outstream
   * @throws IOException
   *     on error
   */
  void setProcessOutputStream(OutputStream outputStream) throws IOException;

  /**
   * set the process error stream.
   *
   * @param inputStream
   *     the inputstream
   * @throws IOException
   *     on error
   */
  void setProcessErrorStream(InputStream inputStream) throws IOException;

  /**
   * Start handling.
   *
   * @throws IOException
   *     on error
   */
  void start() throws IOException;

  /**
   * Stop handling to clean up the streams.
   *
   * @throws IOException
   *     on error
   */
  void stop() throws IOException;

  /**
   * Get the collected stdoutIs.
   *
   * @return the stdoutIs.
   */
  String getStdout();

  /**
   * Get the collected stderrIs.
   *
   * @return the stderrIs.
   */
  String getStderr();
}
