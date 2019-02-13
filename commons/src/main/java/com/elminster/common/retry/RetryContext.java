package com.elminster.common.retry;

/**
 * The retry context.
 *
 * @author jgu
 * @version 1.0
 */
public interface RetryContext {

  /**
   * Set the info about the retry, typically can be the name of the command.
   * 
   * @param info the info about the retry
   */
  public void setInfo(String info);
  
  /**
   * Get the info about the retry, typically can be the name of the command.
   * 
   * @return the info about the retry
   */
  public String getInfo();

  /**
   * Get the retry policy.
   * 
   * @return the retry policy
   */
  public RetryPolicy getRetryPolicy();

  /**
   * Get the interval between two retries.
   * 
   * @return the interval between two retries
   */
  public long getRetryInterval();

  /**
   * To determine if thrown the {@link RetryExhaustedException} when retry gets exhausted.
   * 
   * @return if thrown the {@link RetryExhaustedException} when retry gets exhausted
   */
  public boolean isThrowExhaustedExcetion();
}
