package com.my.service.java8;

import java.text.NumberFormat;

import org.junit.Test;

public class MyThreadTestCase {

  @Test
  public void constructThread() {
    Runnable runnable = () -> {
      System.out.println("Do sth.");
    };
    Thread thread = new Thread(runnable);
    thread.start();

    /**
     * 1. Thread ends when run method ends
     * 2. The current thread can wait for another thread to complete
     * ``` thread.join(milliseconds); ```
     */

  }

  public void interruptThread() {
    /**
     * 1. Stopping a thread is unsafe and deprecated because If a thread holds a lock, then the lock will never be released.
     * 2. A thread can interrupt another thread
     * ``` thread.interrupt() ```  This will simply sets the "interrupted" flag
     * 3. Usually, thread exit safely when they are interrupted.
     * 4. When a thread waits or sleeps while it is interrupted, the "interrupted" flag is not set, an InterruptedException is thrown.
     * 5. Use ThreadLocal<T> to share variable
     */

  }

  @Test
  public void threadLocal() {
    final ThreadLocal<NumberFormat> currencyFormat = ThreadLocal
        .withInitial(() -> NumberFormat.getNumberInstance());

    final NumberFormat format = NumberFormat.getNumberInstance();
    for (int i = 0; i < 100; i++) {
      new Thread(() -> {
        Double r = Math.random() * 100000.01;
        //Use ThreadLocal
        System.out.println(currencyFormat.get().format(r));
        //Don't use ThreadLocal
        //System.out.println(format.format(r));
      }).start();
    }

  }


}
