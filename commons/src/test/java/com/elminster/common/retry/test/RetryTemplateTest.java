package com.elminster.common.retry.test;

import org.junit.Assert;
import org.junit.Test;

import com.elminster.common.retry.RetryContext;
import com.elminster.common.retry.RetryExhaustedException;
import com.elminster.common.retry.RetryOnThrowablePolicy;
import com.elminster.common.retry.RetryTemplate;

public class RetryTemplateTest {

  @Test
  public void testRetryTemplate() {
    try {
      RetryTemplate.TEMPLATE.retry((ctx) -> {
        throw new RuntimeException("test exception.");
      });
    } catch (RuntimeException | RetryExhaustedException e) {
      Assert.assertTrue(e instanceof RetryExhaustedException);
    }

    RetryContext context = new RetryTemplate.RetryContextImpl("testCommand()", new RetryOnThrowablePolicy(), null, 1000L, false);
    
    try {
      RetryTemplate.TEMPLATE.retry((ctx) -> {
        throw new RuntimeException("test exception.");
      }, context);
    } catch (RuntimeException | RetryExhaustedException e) {
      Assert.assertTrue(e instanceof RuntimeException);
    }
  }
}
