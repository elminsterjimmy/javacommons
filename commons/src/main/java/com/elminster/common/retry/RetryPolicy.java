package com.elminster.common.retry;

/**
 * The Retry Policy to determine if the command need to be retried.
 * 
 * @author jgu
 * @version 1.0
 */
public interface RetryPolicy {

  /**
   * To determine if the command need to be retried based on the retry state.
   * 
   * @param retryState the retry state
   * @param context the retry context
   * @return if the command need to be retried
   */
  public boolean needRetry(RetryContext context, RetryState retryState);
}
