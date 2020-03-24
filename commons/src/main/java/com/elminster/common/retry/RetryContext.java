package com.elminster.common.retry;

/**
 * The retry context.
 *
 * @author jgu
 * @version 1.0
 */
public interface RetryContext {

  /**
   * Get the info about the retry, typically can be the name of the command.
   * 
   * @return the info about the retry
   */
  String getInfo();

  /**
   * Get the retry policy.
   * 
   * @return the retry policy
   */
  RetryPolicy getRetryPolicy();

  /**
   * Get the interval between two retries.
   * 
   * @return the interval between two retries
   */
  long getRetryInterval();

  /**
   * To determine if thrown the {@link RetryExhaustedException} when retry gets exhausted.
   * 
   * @return if thrown the {@link RetryExhaustedException} when retry gets exhausted
   */
  boolean isThrowExhaustedExcetion();

  RetryListener getRetryListener();
}
