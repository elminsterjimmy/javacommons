package com.elminster.common.retry;

/**
 * The retry command what always can be a function point to the operation that needs retry feature.
 * 
 * @author jgu
 *
 * @param <R> the return type of the command
 * @param <T> the throwable type of the command
 */
public interface RetryCommand<R, T extends Throwable> {

  /**
   * Execute the command.
   * 
   * @param context the retry context
   * @return the result of the command
   * @throws T the throwable of the command
   */
  public R execute(RetryContext context) throws T;
}
