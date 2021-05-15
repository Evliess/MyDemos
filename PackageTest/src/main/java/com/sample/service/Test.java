package com.sample.service;

public class Test {
  public static void main(String[] args) {
    String str = "Hello";
    char[] arr = str.toCharArray();

    char[] target = new char[str.length()];

    for (int i = str.length() - 1, j = 0; i >= 0; i--) {
      target[j] = arr[i];
      j++;
    }

    String targetStr = new String(target);
    System.out.print(str.equals(targetStr));

  }
}
