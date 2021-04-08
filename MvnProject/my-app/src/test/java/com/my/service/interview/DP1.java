package com.my.service.interview;

public class DP1 {


  public static void main(String[] args) {
    //fun1(5);
    fun(5);
    fun(4);
  }

  static void fun1(int n) {
    int a = 0, b = 1, sum = 0, i;
    for (i = 0; i < n; i++) {
      sum = a + b;
      a = b;
      b = sum;
    }
    System.out.println(sum);
  }


  static void fun(int n) {
    int[] fn = new int[n];
    fn[0] = 1;
    fn[1] = 1;
    for (int i = 2; i < n; i++) {
      fn[i] = fn[i - 1] + fn[i - 2];
    }
    int res = fn[n - 1];
    System.out.println(res);
  }
}
