package com.my.service.interview;

import org.junit.Test;

/*
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个 n 级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
 * f(1) = 1, f (2) = 2, f(3) = 3, f(4) = 5,
 * ==>
 * f(n) = f(n-1) + f(n-2)
 * */
public class DP2 {


  public int jump(int n, int pkg[]) {
    if (n <= 2) {
      return n;
    }
    if (pkg[n] != 0) {
      return pkg[n];
    }
    pkg[n] = jump(n - 1, pkg) + jump(n - 2, pkg);
    return pkg[n];
  }

  public int jump2(int n) {
    if (n <= 2) {
      return n;
    }
    int[] pkg = new int[n + 1];
    pkg[0] = 0;
    pkg[1] = 1;
    pkg[2] = 2;
    for (int j = 3; j <= n; j++) {
      pkg[j] = pkg[j - 1] + pkg[j - 2];
    }
    return pkg[n];
  }

  @Test
  public void test() {
    int n = 40;
    int pkg[] = new int[n + 1];
    System.out.println(jump(n, pkg) + "");
//    System.out.println(jump2(n) + "");

  }

}
