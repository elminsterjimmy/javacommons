package com.elminster.common.retry;

import java.util.Optional;

/**
 * The retry state.
 * 
 * @author jgu
 * @version 1.0
 */
public interface RetryState {

  /**
   * Get the current retry count.
   * 
   * @return the current retry count
   */
  public int getRetryCount();

  /**
   * Get the last throwable from the execution of the command.
   * 
   * @return the last throwable from the execution of the command
   */
  public Optional<Throwable> getLastThrowable();

  /**
   * Get the last return value from the execution of the command.
   * 
   * @return the last return value from the execution of the command
   */
  public Optional<Object> getLastReturnValue();

  /**
   * Get the last timestamp from the execution of the command.
   * 
   * @return the last timestamp from the execution of the command
   */
  public long getLastExecutionTimestamp();
}
