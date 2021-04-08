package com.my.service.interview;

public class SingletonTest {
  private static volatile SingletonTest instance; //加上volatile 禁止指令重排序

  private SingletonTest() {
  }

  public static SingletonTest getInstance() {
    if (instance == null) {
      synchronized (SingletonTest.class) {
        if (instance == null) {
          try {
            Thread.sleep(1);  //Why????
          } catch (Exception e) {
            e.printStackTrace();
          }
          instance = new SingletonTest();
        }
      }
    }
    return instance;
  }
}
