package com.elminster.common.retry;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The stateless retry template.
 * 
 * @author jgu
 * @version 1.0
 */
public class RetryTemplate {

  /** the logger. */
  private static final Logger logger = LoggerFactory.getLogger(RetryTemplate.class);

  /** the singleton retry template. */
  public static final RetryTemplate TEMPLATE = new RetryTemplate();
  
  private RetryTemplate() {}
  
  /**
   * Simple retry the command when any exception thrown.
   * 
   * @param command the command
   * @return the command result if everything's okey
   * @throws T                       the last exception the command thrown
   * @throws RetryExhaustedException on all retries exhausted
   */
  public <R, T extends Throwable> R retry(RetryCommand<R, T> command) throws T, RetryExhaustedException {
    return retry(command, new RetryContextImpl(new RetryOnThrowablePolicy()));
  }

  /**
   * Retry the command with the policy.
   * 
   * @param command the command
   * @param policy the retry policy
   * @return the command result if everything's okey
   * @throws T                       the last exception the command thrown
   * @throws RetryExhaustedException on all retries exhausted
   */
  public <R, T extends Throwable> R retry(RetryCommand<R, T> command, RetryPolicy policy) throws T, RetryExhaustedException {
    return retry(command, new RetryContextImpl(policy));
  }

  /**
   * Retry the command with the retry context.
   * 
   * @param command the command
   * @param context the retry context
   * @return the command result if everything's okey
   * @throws T                       the last exception the command thrown
   * @throws RetryExhaustedException on all retries exhausted
   */
  @SuppressWarnings("unchecked")
  public <R, T extends Throwable> R retry(RetryCommand<R, T> command, RetryContext context) throws T, RetryExhaustedException {
    assert null != context;
    RetryListener retryListener = context.getRetryListener();
    RetryPolicy policy = context.getRetryPolicy();
    RetryStateImpl state = new RetryStateImpl();
    int retryCount = 0;
    Object lastValue = null;
    do {
      logger.info(String.format("Start retry operation [%s], retry count [%d].", context.getInfo(), state.retryCount));
      beforeRetry(retryListener, context, state);
      try {
        state.setLastExecutionTimestamp(System.currentTimeMillis());
        R returnValue = command.execute(context);
        state.setLastReturnValue(returnValue);
        afterRetry(retryListener, context, state);
        if (!policy.needRetry(context, state)) {
          // if doesn't need retry just return the return value
          return returnValue;
        }
      } catch (Throwable throwable) {
        state.setLastThrowable(throwable);
        lastValue = throwable;
        logger.error(throwable.getMessage(), throwable);
        afterRetry(retryListener, context, state);
        if (!policy.needRetry(context, state)) {
          // if doesn't need retry just throw the throwable
          throw throwable;
        }
      }
      // set retry count, sleep retry interval and try to retry
      state.setRetryCount(++retryCount);
      try {
        Thread.sleep(context.getRetryInterval());
      } catch (InterruptedException e) {
        e = null; // ignore the interrupt exception
      }
    } while (policy.needRetry(context, state));
    if (context.isThrowExhaustedExcetion()) {
      throw new RetryExhaustedException();
    } else {
      if (lastValue instanceof Throwable) {
        throw (T) lastValue;
      } else {
        return (R) lastValue;
      }
    }
  }

  private void beforeRetry(RetryListener retryListener, RetryContext context, RetryState state) {
    logger.debug("before retry command: {}, retry count: {}.", context.getInfo(), state.getRetryCount());
    if (null != retryListener) {
      retryListener.beforeRetry(context, state);
    }
  }

  private void afterRetry(RetryListener retryListener, RetryContext context, RetryState state) {
    logger.debug("after retry command: {}, retry count: {}. return value: {}. thrown exception: {}", context.getInfo(), state.getRetryCount(),
        state.getLastReturnValue(), state.getLastThrowable());
    if (null != retryListener) {
      retryListener.afterRetry(context, state);
    }
  }
  
  /**
   * The retry context implementation.
   * 
   * @author jgu
   * @version 1.0
   */
  public static class RetryContextImpl implements RetryContext {

    final String info;
    final RetryPolicy retryPolicy;
    final long retryInterval;
    final boolean throwExhaustedExcetion;
    final RetryListener retryListener;
    

    public RetryContextImpl(RetryPolicy retryPolicy) {
      this("", retryPolicy, null, 1000L, true);
    }
    
    public RetryContextImpl(String info, RetryPolicy retryPolicy, RetryListener retryListener, long retryInterval, boolean throwExhaustedExcetion) {
      super();
      this.info = info;
      this.retryPolicy = retryPolicy;
      this.retryInterval = retryInterval;
      this.retryListener = retryListener;
      this.throwExhaustedExcetion = throwExhaustedExcetion;
    }

    @Override
    public long getRetryInterval() {
      return retryInterval;
    }

    @Override
    public boolean isThrowExhaustedExcetion() {
      return throwExhaustedExcetion;
    }

    @Override
    public String getInfo() {
      return info;
    }

    @Override
    public RetryPolicy getRetryPolicy() {
      return retryPolicy;
    }

    @Override
    public RetryListener getRetryListener() {
      return retryListener;
    }
  }

  /**
   * The retry state.
   * 
   * @author jgu
   * @version 1.0
   */
  class RetryStateImpl implements RetryState {
    int retryCount;
    Throwable lastThrowable;
    Object lastReturnValue;
    long lastExecutionTimestamp;

    /**
     * @return the retryCount
     */
    public int getRetryCount() {
      return retryCount;
    }

    /**
     * @param retryCount the retryCount to set
     */
    public void setRetryCount(int retryCount) {
      this.retryCount = retryCount;
    }

    /**
     * @return the lastThrowable
     */
    public Optional<Throwable> getLastThrowable() {
      return Optional.ofNullable(lastThrowable);
    }

    /**
     * @param lastThrowable the lastThrowable to set
     */
    public void setLastThrowable(Throwable lastThrowable) {
      this.lastThrowable = lastThrowable;
    }

    /**
     * @return the lastReturnValue
     */
    public Optional<Object> getLastReturnValue() {
      return Optional.ofNullable(lastReturnValue);
    }

    /**
     * @param lastReturnValue the lastReturnValue to set
     */
    public void setLastReturnValue(Object lastReturnValue) {
      this.lastReturnValue = lastReturnValue;
    }

    /**
     * @return the lastExecutionTimestamp
     */
    public long getLastExecutionTimestamp() {
      return lastExecutionTimestamp;
    }

    /**
     * @param lastExecutionTimestamp the lastExecutionTimestamp to set
     */
    public void setLastExecutionTimestamp(long lastExecutionTimestamp) {
      this.lastExecutionTimestamp = lastExecutionTimestamp;
    }
  }
}
