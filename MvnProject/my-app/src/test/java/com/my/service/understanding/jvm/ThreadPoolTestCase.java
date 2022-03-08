package com.my.service.understanding.jvm;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.*;

public class ThreadPoolTestCase {
  private Logger logger = LoggerFactory.getLogger(ThreadPoolTestCase.class);


  @Before
  public void before() {
  }

  @Test
  public void defaultAbortPolicy() throws InterruptedException {
    ThreadPoolExecutor executor = new ThreadPoolExecutor(
        2, 5,
        0, TimeUnit.MILLISECONDS,
        new LinkedBlockingDeque<>(10),
        Executors.defaultThreadFactory(),
        new ThreadPoolExecutor.AbortPolicy());
    for (int i = 0; i < 16; i++) {
      try {
        int taskNum = i;
        executor.execute(() -> {
          logger.info("Thread Name: {}, Task Name: {}", Thread.currentThread().getName(), taskNum + "");
        });
      } catch (RejectedExecutionException e) {
        logger.error("{}", e);
      }
    }
    Thread.currentThread().join();
  }

  @Test
  public void callerRunsPolicy() throws InterruptedException {
    ThreadPoolExecutor executor = new ThreadPoolExecutor(
        2, 5,
        0, TimeUnit.MILLISECONDS,
        new LinkedBlockingDeque<>(10),
        Executors.defaultThreadFactory(),
        new ThreadPoolExecutor.CallerRunsPolicy());
    for (int i = 0; i < 16; i++) {
      int taskNum = i;
      executor.execute(() -> {
        logger.info("Thread Name: {}, Task Name: {}", Thread.currentThread().getName(), taskNum + "");
      });
    }
    Thread.currentThread().join();
  }

  @Test
  public void discardPolicy() throws InterruptedException {
    ThreadPoolExecutor executor = new ThreadPoolExecutor(
        2, 5,
        0, TimeUnit.MILLISECONDS,
        new LinkedBlockingDeque<>(10),
        Executors.defaultThreadFactory(),
        new ThreadPoolExecutor.DiscardPolicy());
    for (int i = 0; i < 16; i++) {
      int taskNum = i;
      executor.execute(() -> {
        logger.info("Thread Name: {}, Task Name: {}", Thread.currentThread().getName(), taskNum + "");
      });

    }
    Thread.currentThread().join();
  }

  @Test
  public void discardOldestPolicy() throws InterruptedException {
    ThreadPoolExecutor executor = new ThreadPoolExecutor(
        2, 5,
        0, TimeUnit.MILLISECONDS,
        new LinkedBlockingDeque<>(10),
        Executors.defaultThreadFactory(),
        new ThreadPoolExecutor.DiscardOldestPolicy());
    for (int i = 0; i < 20; i++) {
      int taskNum = i;
      executor.execute(() -> {
        logger.info("Thread Name: {}, Task Name: {}", Thread.currentThread().getName(), taskNum + "");
      });
    }
    Thread.currentThread().join();
  }

  @Test
  public void myAbortPolicy() throws InterruptedException {
    ThreadPoolExecutor executor = new ThreadPoolExecutor(
        2, 5,
        0, TimeUnit.MILLISECONDS,
        new LinkedBlockingDeque<>(10),
        Executors.defaultThreadFactory(),
        new MyAbortPolicy());
    for (int i = 0; i < 20; i++) {
      int taskNum = i;
      executor.execute(() -> {
        logger.info("Thread Name: {}, Task Name: {}", Thread.currentThread().getName(), taskNum + "");
      });
    }
    Thread.currentThread().join();
  }
}

class MyAbortPolicy implements RejectedExecutionHandler {
  private Logger logger = LoggerFactory.getLogger(MyAbortPolicy.class);

  @Override
  public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
    logger.warn("Thread pool is lack of resource! Pool Size: {}, Active Count: {}, Core Pool Size: {}, Max Pool Size: {}",
        e.getPoolSize(), e.getActiveCount(), e.getCorePoolSize(), e.getMaximumPoolSize());
    final Thread t = new Thread(r, "New Thread:" + new Date().getTime());
    t.start();
  }
}


