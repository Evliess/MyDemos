package com.my.service.understanding.jvm;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

public class ObjectLengthTest {

  @Test
  public void fun() {
    Object o = new Object();
    System.out.println(ClassLayout.parseInstance(o).toPrintable());
  }

  @Test
  public void fun1() {
    System.out.println("Integer:" + Integer.SIZE / 8); // Integer:4
    System.out.println("Short:" + Short.SIZE / 8); // Short:2
    System.out.println("Long:" + Long.SIZE / 8); // Long:8
    System.out.println("Byte:" + Byte.SIZE / 8); // Byte:1
    System.out.println("Character:" + Character.SIZE / 8); // Character:2
    System.out.println("Float:" + Float.SIZE / 8); // Float:4
    System.out.println("Double:" + Double.SIZE / 8); // Double:8
  }

  @Test
  public void fun2() {
    AAA a = new AAA();
    System.out.println(ClassLayout.parseInstance(a).toPrintable());
  }


  class AAA {
    double m;
    boolean n;
    String a = "helloworld!";
  }
}
