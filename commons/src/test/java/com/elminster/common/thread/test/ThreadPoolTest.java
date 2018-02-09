package com.elminster.common.thread.test;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Test;

import com.elminster.common.threadpool.FutureEvent;
import com.elminster.common.threadpool.FutureListener;
import com.elminster.common.threadpool.ListenableFuture;
import com.elminster.common.threadpool.ThreadPool;
import com.elminster.common.threadpool.ThreadPoolConfiguration;

public class ThreadPoolTest {

  private static final ThreadPool tp = ThreadPool.createThreadPool(new ThreadPoolConfiguration());
  
  @Test
  public void testFutureMonitor() {
    Callable<String> callable = new Callable<String>() {

      @Override
      public String call() throws Exception {
        Thread.sleep(2000);
        return "ok";
      }
      
    };
    Future<String> future = tp.submit(callable);
    if (future instanceof ListenableFuture) {
      ListenableFuture<String> lf = (ListenableFuture<String>) future;
      lf.addListener(new FutureListener() {
        
        @Override
        public void onFinished(FutureEvent event) {
          Assert.assertEquals("ok", (String) event.getTarget());
        }
        
        @Override
        public void onException(FutureEvent event) {
        }
        
        @Override
        public void onCancelled(FutureEvent event) {
        }
      });
    }
    
    Callable<String> cancelledCallable = new Callable<String>() {

      @Override
      public String call() throws Exception {
        Thread.sleep(2000);
        return "ok";
      }
      
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

    Callable<String> exceptionCallable = new Callable<String>() {

      @Override
      public String call() throws Exception {
        throw new IllegalStateException("illegal state!");
      }
      
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
          Assert.assertEquals(t.getCause().getMessage(), "illegal state!");
        }
        
        @Override
        public void onCancelled(FutureEvent event) {
        }
      });
    }

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
