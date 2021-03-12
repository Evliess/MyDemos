package com.my.service.interview;

import java.util.Scanner;

public class HJ15 {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int num = sc.nextInt();
    int count = 0;
    while (num > 0) {
      if ((num & 1) != 0) {
        count++;
      }
      num = num >> 1;
    }
    System.out.println(count);
  }



  public static void fun() {
    Scanner sc = new Scanner(System.in);
    int num = sc.nextInt();
    char[] arr = Integer.toBinaryString(num).toCharArray();
    int count = 0;
    for (char a : arr) {
      if (a == '1') {
        count++;
      }
    }
    System.out.println(count);
  }
}
