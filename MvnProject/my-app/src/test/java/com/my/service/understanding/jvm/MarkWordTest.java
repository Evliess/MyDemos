package com.my.service.understanding.jvm;

import org.openjdk.jol.info.ClassLayout;

public class MarkWordTest {

  public static void main(String[] args) {
    MarkWordTest t = new MarkWordTest();
    System.out.println(ClassLayout.parseInstance(t).toPrintable());
    synchronized (t) {
      System.out.println(ClassLayout.parseInstance(t).toPrintable());
    }
    System.out.println(ClassLayout.parseInstance(t).toPrintable());
  }
}
