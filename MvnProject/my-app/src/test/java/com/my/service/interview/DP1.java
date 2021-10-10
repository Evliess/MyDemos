package com.my.service.interview;

import org.junit.Test;


//Fibonacci sequence
public class DP1 {

  //递归的解法
  public int fib1(int n) {
    if (n == 0 | n == 1) {
      return 1;
    } else {
      return fib1(n - 1) + fib1(n - 2);
    }
  }

  //备忘录的解法
  public int fib2(int memo[], int n) {
    if (n >= 2) {
      memo[n] = fib2(memo, n - 1) + fib2(memo, n - 2);
      return memo[n];
    } else {
      return 1;
    }
  }

  @Test
  public void fib1_t() {
    System.out.println(fib1(2));
  }

  @Test
  public void fib2_t() {
    int n = 2;
    int memo[] = new int[n + 1];
    System.out.println(fib2(memo, 2));
  }

}
