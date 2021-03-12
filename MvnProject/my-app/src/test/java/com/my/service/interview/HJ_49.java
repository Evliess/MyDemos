package com.my.service.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HJ_49 {

  public static void main(String[] args) throws InterruptedException {
    MyReentrantLock myReentrantLock = new MyReentrantLock();
    ExecutorService pool = Executors.newSingleThreadExecutor();
    for (int i = 0; i < 10; i++) {
      pool.submit(new MyThread(myReentrantLock, "A"));
      pool.submit(new MyThread(myReentrantLock, "B"));
      pool.submit(new MyThread(myReentrantLock, "C"));
      pool.submit(new MyThread(myReentrantLock, "D"));
    }
    pool.shutdown();
    pool.awaitTermination(3, TimeUnit.SECONDS);
    myReentrantLock.print();
  }


}


class MyThread implements Runnable {
  private MyReentrantLock myReentrantLock;

  private String word;

  public MyThread(MyReentrantLock myReentrantLock, String word) {
    this.myReentrantLock = myReentrantLock;
    this.word = word;
  }

  @Override
  public void run() {
    try {
      if (this.word.equals("A")) {
        this.myReentrantLock.putA();
      }
      if (this.word.equals("B")) {
        this.myReentrantLock.putB();
      }
      if (this.word.equals("C")) {
        this.myReentrantLock.putC();
      }
      if (this.word.equals("D")) {
        this.myReentrantLock.putD();
      }

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}


class MyReentrantLock {
  final Lock lock = new ReentrantLock();

  final List<String> items = new ArrayList<>();

  public void print() {
    lock.lock();
    try{
      items.forEach(str -> System.out.print(str));
    }finally {
      lock.unlock();
    }

  }

  public String getLast() {
    if (!this.items.isEmpty()) {
      return this.items.get(this.items.size() - 1);
    } else {
      return "";
    }
  }

  public void putA() throws InterruptedException {
    lock.lock();
    try {
      if (this.items.isEmpty() || "D".equals(this.getLast())) {
        this.items.add("A");
      }
    } finally {
      lock.unlock();
    }
  }

  public void putB() throws InterruptedException {
    lock.lock();
    try {
      if ("A".equals(this.getLast())) {
        this.items.add("B");
      }
    } finally {
      lock.unlock();
    }
  }

  public void putC() throws InterruptedException {
    lock.lock();
    try {
      if ("B".equals(this.getLast())) {
        this.items.add("C");
      }
    } finally {
      lock.unlock();
    }
  }

  public void putD() throws InterruptedException {
    lock.lock();
    try {
      if ("C".equals(this.getLast())) {
        this.items.add("D");
      }
    } finally {
      lock.unlock();
    }
  }
}
