package com.my.service.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HJ_50 {

  public static void main(String[] args) throws InterruptedException {
    BoundedArray boundedArray = new BoundedArray();
    ExecutorService executorService = Executors.newCachedThreadPool();
    for (int i = 0; i < 10; i++) {
      executorService.submit(new MyAThread(boundedArray));
      executorService.submit(new MyBThread(boundedArray));
    }
    executorService.shutdown();
    executorService.awaitTermination(100, TimeUnit.MILLISECONDS);
    boundedArray.print();
    int a = 1;
    int b = 1;
    assert a == b;
    System.out.println("End");

  }
}


class BoundedArray {
  final Lock lock = new ReentrantLock();

  final Condition canPutA = lock.newCondition();

  final Condition canPutB = lock.newCondition();

  final List<String> codes = new ArrayList<>();

  public String getLast() {
    return this.codes.isEmpty() ? "" : this.codes.get(this.codes.size() - 1);
  }

  public void print() {
    this.codes.forEach(str -> System.out.print(str));
  }

  public void putA() {
    lock.lock();
    try {
      while ("A".equals(this.getLast())) {
        canPutA.await();
      }
      this.codes.add("A");
      canPutB.signal();

    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }

  }

  public void putB() {
    lock.lock();
    try {
      while (this.codes.isEmpty() || "B".equals(this.getLast())) {
        canPutB.await();
      }
      this.codes.add("B");
      canPutA.signal();

    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }

  }
}


class MyAThread implements Runnable {

  private BoundedArray boundedArray;

  public MyAThread(BoundedArray boundedArray) {
    this.boundedArray = boundedArray;
  }

  @Override
  public void run() {
    boundedArray.putA();
  }
}


class MyBThread implements Runnable {

  private BoundedArray boundedArray;

  public MyBThread(BoundedArray boundedArray) {
    this.boundedArray = boundedArray;
  }

  @Override
  public void run() {
    boundedArray.putB();
  }
}



//Example


class BoundedBuffer {
  final Lock lock = new ReentrantLock();

  final Condition notFull = lock.newCondition();

  final Condition notEmpty = lock.newCondition();

  final Object[] items = new Object[100];

  int putptr, takeptr, count;

  public void put(Object x) throws InterruptedException {
    lock.lock();
    try {
      while (count == items.length) {
        notFull.await();
      }
      items[putptr] = x;
      if (++putptr == items.length) {
        putptr = 0;
      }
      ++count;
      notEmpty.signal();
    } finally {
      lock.unlock();
    }
  }

  public Object take() throws InterruptedException {
    lock.lock();
    try {
      while (count == 0) {
        notEmpty.await();
      }
      Object x = items[takeptr];
      if (++takeptr == items.length) {
        takeptr = 0;
      }
      --count;
      notFull.signal();
      return x;
    } finally {
      lock.unlock();
    }
  }
}
