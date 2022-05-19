package com.my.service.interview;

import org.junit.Test;

import java.util.Arrays;

/**
 * https://leetcode.cn/problems/coin-change/solution/dong-tai-gui-hua-tao-lu-xiang-jie-by-wei-lai-bu-ke/
 * [1,7,10]  14
 */


public class DP3 {

  private int coinChange(int[] coins, int amount) {
    if (amount == 0) return 0;
    int pkg[] = new int[amount + 1];
    Arrays.fill(pkg, amount + 1);
    pkg[0] = 0;
    for (int i = 1; i <= amount; i++) {
      for (int j = 0; j < coins.length; j++) {
        if (coins[j] <= i) {
          pkg[i] = Math.min(pkg[i], pkg[i - coins[j]] + 1);
        }
      }
    }
    return pkg[amount] > amount ? -1 : pkg[amount];
  }

  @Test
  public void fun() {
//    int[] coins = {411,412,413,414,415,416,417,418,419,420,421,422};
//    int amount = 9864;

    int[] coins = {186, 419, 83, 408};
    int amount = 6249;
    System.out.println(coinChange(coins, amount));
  }


}
