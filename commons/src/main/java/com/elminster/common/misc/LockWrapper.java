package com.elminster.common.misc;

import com.elminster.common.function.Action;

import java.util.concurrent.locks.Lock;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * The lock wrapper.
 *
 * @author jgu
 * @version 1.0
 */
public class LockWrapper {

  public static <T> T wrapperWithLock(Lock lock, Supplier<T> supplier) {
    try {
      lock.lock();
      return supplier.get();
    } finally {
      lock.unlock();
    }
  }

  public static <T> void wrapperWithLock(Lock lock, Consumer<T> consumer, T arg) {
    try {
      lock.lock();
      consumer.accept(arg);
    } finally {
      lock.unlock();
    }
  }

  public static <T, R> R wrapperWithLock(Lock lock, Function<T, R> function, T arg) {
    try {
      lock.lock();
      return function.apply(arg);
    } finally {
      lock.unlock();
    }
  }

  public static void wrapperWithLock(Lock lock, Action action) {
    try {
      lock.lock();
      action.execute();
    } finally {
      lock.unlock();
    }
  }
}
