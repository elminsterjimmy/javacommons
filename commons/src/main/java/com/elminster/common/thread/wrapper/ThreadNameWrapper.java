package com.elminster.common.thread.wrapper;

import com.elminster.common.function.Action;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A wrapper to replace thread name since the thread used can be in a thread pool.
 *
 * @author jgu
 * @version 1.0
 */
public class ThreadNameWrapper {

  /**
   * Replace the thread in one execution lifecycle, after the lifecycle, it will restore the old thread
   * name back.
   *
   * @param consumer
   *     the consumer that provides the old thread name.
   */
  public static void replaceThreadName(Consumer<String> consumer) {
    String oldThreadName = Thread.currentThread().getName();
    try {
      consumer.accept(oldThreadName);
    } finally {
      Thread.currentThread().setName(oldThreadName);
    }
  }

  /**
   * Replace the thread in one execution lifecycle, after the lifecycle, it will restore the old thread
   * name back.
   *
   * @param func
   *     the function that provides the old thread name.
   */
  public static <T> T replaceThreadName(Function<String, T> func) {
    String oldThreadName = Thread.currentThread().getName();
    try {
      return func.apply(oldThreadName);
    } finally {
      Thread.currentThread().setName(oldThreadName);
    }
  }
}
