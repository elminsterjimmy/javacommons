package com.elminster.common.exec;

/**
 * The command line execute result handler. It will be called back
 * when the command line execution finished or met an exception.
 * 
 * @author jgu
 * @version 1.0
 */
public interface ExecuteResultHandler {

  /**
   * Callback on the execution finished.
   * 
   * @param exitValue the exitValue
   */
  public void onFinish(int exitValue);
  
  /**
   * Callback on exception occurred.
   * @param exception exception
   */
  public void onException(Exception exception);
}
