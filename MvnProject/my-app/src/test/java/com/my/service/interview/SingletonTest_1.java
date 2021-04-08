package com.my.service.interview;

public class SingletonTest_1 {
  private SingletonTest_1() {
  }

  public static SingletonTest_1 getInstance() {
    return SingletonHelper.instance;
  }


  private static class SingletonHelper {
    private static final SingletonTest_1 instance = new SingletonTest_1();
  }
}
