package com.elminster.common.retry;

import com.elminster.common.listener.Listener;

public interface RetryListener extends Listener {

    void beforeRetry(RetryContext context, RetryState state);

    void afterRetry(RetryContext context, RetryState state);
}
