package com.my.service.java8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import org.junit.Test;

public class SemaphoreTestCase {

  @Test
  public void test() {
    int threadCount = 200;
    ExecutorService exec = Executors.newCachedThreadPool();
    final Semaphore semaphore = new Semaphore(10);
    for (int i = 0; i < threadCount; i++) {
      final int threadNum = i;
      exec.execute(() -> {
        try {
          System.out.println("获取当前可用的许可证数量---" + semaphore.availablePermits());
          semaphore.acquire();
          System.out.println("Thread Num start:" + threadNum);
          Thread.sleep(1000);
          System.out.println("Thread Num end:" + threadNum);
          semaphore.release();
          System.out.println("获取当前可用的许可证数量：release 1 个---" + semaphore.availablePermits());
        } catch (Exception e) {
          System.out.println(e);
        } finally {
          semaphore.release();
        }
      });

    }
    System.out.println("finish");
    exec.shutdown();
  }
}

