package com.my.service.dp.singleton;

public class SingletonDemo_3 {
  private SingletonDemo_3() {
  }

  public SingletonDemo_3 getInstance() {
    return Holder.instance;
  }

  private static class Holder {
    private static final SingletonDemo_3 instance = new SingletonDemo_3();
  }

}
