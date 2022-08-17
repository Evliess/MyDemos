package com.my.service.dp.singleton;

public class SingletonDemo_1 {

  private final static SingletonDemo_1 singletonDemo_1 = new SingletonDemo_1();

  private SingletonDemo_1() {
  }

  public static SingletonDemo_1 getInstance() {
    return singletonDemo_1;
  }

}
