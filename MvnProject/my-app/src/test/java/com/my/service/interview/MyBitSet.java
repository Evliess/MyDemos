package com.my.service.interview;

import java.util.BitSet;

public class MyBitSet {

  public static void main(String[] args) {
    fun1();
  }



  static void fun1() {
    int[] source = {4, 5, 6, 9};
    int max = source[0];
    for (int i = 1; i < source.length; i++) {
      if (source[i] > max) {
        max = source[i];
      }
    }
    System.out.println("Max number is: " + max);
    BitSet bitSet = new BitSet(max + 1);
    for (int j = 0; j < source.length; j++) {
      bitSet.set(source[j], true);
    }

    System.out.println(bitSet.get(6));
    System.out.println(bitSet.get(7));
  }

}
