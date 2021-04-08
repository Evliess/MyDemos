package com.my.service.dp;

public class SingletonDemo_2 {

  private volatile static SingletonDemo_2 singletonDemo2;

  private SingletonDemo_2() {
  }

  public static SingletonDemo_2 getInstance() {
    if (singletonDemo2 == null) {
      synchronized (SingletonDemo_2.class) {
        if (singletonDemo2 == null) {
          singletonDemo2 = new SingletonDemo_2();
        }
      }
    }
    return singletonDemo2;
  }

}
