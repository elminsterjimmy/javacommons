package com.elminster.common.thread.test;

import com.elminster.common.thread.threadpool.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

  private static final ThreadPool tp = ThreadPool.createThreadPool(new ThreadPoolConfiguration());
  
  @Test
  public void testFutureMonitor() {
    ThreadPoolJVMShutdownHook shutdownHook = new ThreadPoolJVMShutdownHook(tp);
    shutdownHook.attachToJVMRuntime();

    Callable<String> callable = () -> {
      Thread.sleep(2000);
      return "ok";
    };
    Future<String> future = tp.submit(callable);
    if (future instanceof ListenableFuture) {
      ListenableFuture<String> lf = (ListenableFuture<String>) future;
      lf.addListener(new FutureListener() {
        
        @Override
        public void onFinished(FutureEvent event) {
          Assert.assertEquals("ok", event.getTarget());
        }
        
        @Override
        public void onException(FutureEvent event) {
        }
        
        @Override
        public void onCancelled(FutureEvent event) {
        }
      });
    }
    
    Callable<String> cancelledCallable = () -> {
      Thread.sleep(2000);
      return "ok";
    };
    Future<String> cancelledFuture = tp.submit(cancelledCallable);
    if (cancelledFuture instanceof ListenableFuture) {
      ListenableFuture<String> lf = (ListenableFuture<String>) cancelledFuture;
      lf.addListener(new FutureListener() {
        
        @Override
        public void onFinished(FutureEvent event) {
        }
        
        @Override
        public void onException(FutureEvent event) {
        }
        
        @Override
        public void onCancelled(FutureEvent event) {
          Assert.assertNotNull(event);
        }
      });
    }
    cancelledFuture.cancel(true);

    Callable<String> exceptionCallable = () -> {
      throw new IllegalStateException("illegal state!");
    };
    Future<String> exceptionFuture = tp.submit(exceptionCallable);
    if (exceptionFuture instanceof ListenableFuture) {
      ListenableFuture<String> lf = (ListenableFuture<String>) exceptionFuture;
      lf.addListener(new FutureListener() {
        
        @Override
        public void onFinished(FutureEvent event) {
        }
        
        @Override
        public void onException(FutureEvent event) {
          Throwable t = (Throwable) event.getTarget();
          Assert.assertEquals("java.lang.IllegalStateException: illegal state!", t.getCause().getMessage());
        }
        
        @Override
        public void onCancelled(FutureEvent event) {
        }
      });
    }

    tp.shutdown(5, TimeUnit.SECONDS);
  }
}
