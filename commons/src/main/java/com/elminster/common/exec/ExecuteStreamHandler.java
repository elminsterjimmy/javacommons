package com.elminster.common.exec;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Redirct the execute streams.
 * 
 * @author jgu
 * @version 1.0
 */
public interface ExecuteStreamHandler {

  /**
   * set the process input stream.
   * 
   * @param inputStream the inputstream
   * @throws IOException on error
   */
  public void setProcessInputStream(InputStream inputStream) throws IOException;
  
  /**
   * set the process out stream.
   * 
   * @param outputStream the outstream
   * @throws IOException on error
   */
  public void setProcessOutputStream(OutputStream outputStream) throws IOException;
  
  /**
   * set the process error stream.
   * 
   * @param inputStream the inputstream
   * @throws IOException on error
   */
  public void setProcessErrorStream(InputStream inputStream) throws IOException;
  
  /**
   * Start handling.
   * @throws IOException on error
   */
  public void start() throws IOException;
  
  /**
   * Stop handling.
   * @throws IOException on error
   */
  public void stop() throws IOException;
}
