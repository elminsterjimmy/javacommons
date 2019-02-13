package com.elminster.common.retry;

import java.util.Collections;
import java.util.Set;

/**
 * Retry the command on specified Throwable thrown.
 * 
 * @author jgu
 * @version 1.0
 */
public class RetryOnThrowablePolicy implements RetryPolicy {

  private static final int DEFAULT_MAX_RETRY_COUNT = 3;
  private final int maxRetryCount;
  private final Set<Class<? extends Throwable>> throwableClassesNeedToRetry;

  public RetryOnThrowablePolicy() {
    this(DEFAULT_MAX_RETRY_COUNT, Collections.singleton(Throwable.class));
  }
  
  public RetryOnThrowablePolicy(Set<Class<? extends Throwable>> throwableClassesNeedToRetry) {
    this(DEFAULT_MAX_RETRY_COUNT, throwableClassesNeedToRetry);
  }

  public RetryOnThrowablePolicy(int maxRetryCount, Set<Class<? extends Throwable>> throwableClassesNeedToRetry) {
    this.maxRetryCount = maxRetryCount;
    this.throwableClassesNeedToRetry = throwableClassesNeedToRetry;
  }

  @Override
  public boolean needRetry(RetryContext context, RetryState retryState) {
    return isThrowableClassNeedToRetry(retryState.getLastThrowable().get()) && hasNotExhauted(retryState.getRetryCount());
  }

  private boolean hasNotExhauted(int retryCount) {
    return retryCount <= maxRetryCount;
  }

  private boolean isThrowableClassNeedToRetry(Throwable lastThrowable) {
    for (Class<? extends Throwable> throwableClassNeedToRetry : throwableClassesNeedToRetry) {
      if (throwableClassNeedToRetry.isAssignableFrom(lastThrowable.getClass())) {
        return true;
      }
    }
    return false;
  }
}
