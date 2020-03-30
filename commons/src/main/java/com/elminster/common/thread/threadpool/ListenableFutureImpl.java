package com.elminster.common.thread.threadpool;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * A future with listener(s).
 * 
 * @author jinggu
 * @version 1.0
 *
 * @param <T>
 *          The result type returned by this Future's {@code get} method
 */
public class ListenableFutureImpl<T> implements ListenableFuture<T>, ScheduledFuture<T> {

  /** the future to observer. */
  private Future<T> future;
  private List<FutureListener> listeners = new LinkedList<>();

  /**
   * Constructor.
   * 
   * @param future
   *          the future to observer
   */
  public ListenableFutureImpl(Future<T> future) {
    this.future = future;
    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean cancel(boolean mayInterruptIfRunning) {
    return future.cancel(mayInterruptIfRunning);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isCancelled() {
    return future.isCancelled();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDone() {
    return future.isDone();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T get() throws InterruptedException, ExecutionException {
    return future.get();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
    return future.get(timeout, unit);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int compareTo(Delayed o) {
    if (future instanceof Delayed) {
      Delayed delayed = (Delayed) future;
      return delayed.compareTo(delayed);
    } else {
      throw new UnsupportedOperationException("");
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getDelay(TimeUnit unit) {
    if (future instanceof Delayed) {
      Delayed delayed = (Delayed) future;
      return delayed.getDelay(unit);
    }
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addListener(FutureListener listener) {
    this.listeners.add(listener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<FutureListener> getListeners() {
    return Collections.unmodifiableCollection(listeners);
  }
}
