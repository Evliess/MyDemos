package com.sample.service;

public class MyTest {

  public static void main(String[] args) {
    String str = "bbcaaaaaacbb";


    /**
     * aabbcca
     * aabaa
     */
    char[] array = str.toCharArray();
    boolean flag = true;
    int length = str.length();
    int middle = (length - 1) / 2;
    for (int i = 0; i < middle; i++) {
      if (array[i] != array[length - 1 - i]) {
        flag = false;
        break;
      }
    }

    System.out.println(flag);
  }
}
