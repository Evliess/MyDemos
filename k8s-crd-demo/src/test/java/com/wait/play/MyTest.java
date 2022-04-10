package com.wait.play;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;

public class MyTest {

  @Test
  public void fun() throws InterruptedException {
    ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue(1024);

    arrayBlockingQueue.take();

    System.out.println("111111");
  }
}
