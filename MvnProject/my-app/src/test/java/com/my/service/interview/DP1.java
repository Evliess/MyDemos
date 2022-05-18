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

  public int fib3(int n) {
    if (n <= 1) {
      return 1;
    }
    int[] pkg = new int[n + 1];
    pkg[0] = 1;
    pkg[1] = 1;
    for (int j=2; j<=n; j++) {
      pkg[j] = pkg[j-1] + pkg[j-2];
    }
    return pkg[n];
  }

  @Test
  public void fib1_t() {
    int n = 40;
    System.out.println(fib1(n));
    System.out.println(fib3(n));
  }

  @Test
  public void fib2_t() {
    int n = 50;
    int memo[] = new int[n + 1];
    System.out.println(fib3(n));
    System.out.println(fib2(memo, n));
  }

}
