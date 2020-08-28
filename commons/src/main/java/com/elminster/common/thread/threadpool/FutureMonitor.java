package com.elminster.common.thread.threadpool;

import com.elminster.common.misc.LockWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The future monitor with reactor pattern.
 *
 * Notice that if the {@link ListenableFuture}'s listener registered after the
 * Future completed, it will not get the notification of the {@link FutureEvent}.
 *
 * @author jinggu
 * @version 1.0
 */
public class FutureMonitor implements Runnable {

  private static final Logger logger = LoggerFactory.getLogger(FutureMonitor.class);

  private final int maxFutureToMonitor;
  private final List<ListenableFuture<?>> monitoredFutures;
  private ReadWriteLock lock = new ReentrantReadWriteLock();
  private Lock readLock = lock.readLock();
  private Lock writeLock = lock.writeLock();

  public FutureMonitor(int maxFutureToMonitor) {
    this.maxFutureToMonitor = maxFutureToMonitor;
    this.monitoredFutures = Collections.synchronizedList(new LinkedList<>());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {
    while (!Thread.interrupted()) {
      List<FutureEvent> events = collect();
      for (FutureEvent event : events) {
        dispatch(event);
      }
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        logger.error(String.format("Future Monitor has been interrupted! Current Monitoring Future Count [%d].", monitoredFutures.size()));
      }
    }
  }

  /**
   * Collect the events.
   *
   * @return the events
   */
  private List<FutureEvent> collect() {
    return LockWrapper.wrapperWithLock(readLock, () -> {
      List<FutureEvent> events = new ArrayList<>();
      for (ListenableFuture<?> future : monitoredFutures) {
        if (future.isDone()) {
          FutureEvent event;
          if (future.isCancelled()) {
            event = new FutureCancelledEvent(future, null);
          } else {
            try {
              Object rtn = future.get();
              event = new FutureFinishedEvent(future, rtn);
            } catch (InterruptedException | ExecutionException e) {
              event = new FutureExceptionEvent(future, e);
            }
          }
          events.add(event);
        }
      }
      return events;
    });
  }

  /**
   * Dispatch the event.
   *
   * @param event
   *     the event
   */
  private void dispatch(FutureEvent event) {
    ListenableFuture<?> listenableFuture = (ListenableFuture<?>) event.getSource();
    Collection<FutureListener> listeners = listenableFuture.getListeners();
    for (FutureListener listener : listeners) {
      if (event instanceof FutureCancelledEvent) {
        listener.onCancelled(event);
      } else if (event instanceof FutureExceptionEvent) {
        listener.onException(event);
      } else if (event instanceof FutureFinishedEvent) {
        listener.onFinished(event);
      }
    }
    LockWrapper.wrapperWithLock(writeLock, () -> monitoredFutures.remove(listenableFuture));
  }

  /**
   * Add the future to monitor.
   *
   * @param future
   *     the future to monitor
   * @throws FutureOverflowException
   *     if exceed the max monitor future count
   */
  public void addFuture(ListenableFuture<?> future) throws FutureOverflowException {
    LockWrapper.wrapperWithLock(writeLock, () -> {
      if (monitoredFutures.size() > maxFutureToMonitor) {
        throw new FutureOverflowException();
      }
      monitoredFutures.add(future);
    });
  }

  public int getMaxFutureToMonitor() {
    return maxFutureToMonitor;
  }

  public Collection<ListenableFuture<?>> getMonitoredFutures() {
    return Collections.unmodifiableCollection(monitoredFutures);
  }
}