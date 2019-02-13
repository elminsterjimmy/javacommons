package com.elminster.common.retry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Retryable annotation for AOP.
 * 
 * @author jgu
 * @version 1.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Retryable {

  /**
   * The class name of the retry policy.
   * 
   * @return the class name of the retry policy
   */
  String policy();

  /**
   * The max retry count of the retry policy. default: <code>3</code>
   * 
   * @return the max retry count of the retry policy
   */
  int maxRetryCount() default 3;

  /**
   * The interval between 2 retries. default: <code>1000</code> ms.
   * 
   * @return the interval between 2 retries
   */
  long retryInterval() default 1000L;
}
