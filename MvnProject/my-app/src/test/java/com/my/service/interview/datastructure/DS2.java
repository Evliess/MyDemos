package com.my.service.interview.datastructure;

import org.junit.Test;

/**
 * https://leetcode.cn/problems/maximum-subarray/
 */
public class DS2 {
  public int maxSubArray(int[] nums) {
    int len = nums.length;
    int dp[] = new int[len];
    dp[0] = nums[0];
    int res = dp[0];
    for (int i = 1; i < len; i++) {
      if (dp[i - 1] > 0) {
        dp[i] = dp[i - 1] + nums[i];
      } else {
        dp[i] = nums[i];
      }
      res = Math.max(res, dp[i]);
    }
    return res;
  }

  @Test
  public void fun() {
    int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
    System.out.println(maxSubArray(nums));
  }

  @Test
  public void fun1() {
    int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
    int ans = Integer.MIN_VALUE;
    int k = 2;
    for (int i = 0; i < nums.length - k + 1; i++) {
      int sum = 0;
      for (int j = 0; j < k; j++) {
        if (i + j < nums.length) {
          sum += nums[i + j];
        }
      }
      ans = sum > ans ? sum : ans;
      System.out.println(sum);
    }
    System.out.println("ans: " + ans);
  }

  @Test
  //timeout!!!
  public void fun2() {
    int[] nums = {5,4,-1,7,8};
    int ans = Integer.MIN_VALUE;
    for (int k = 1; k <= nums.length; k++) {
      for (int i = 0; i < nums.length - k + 1; i++) {
        int sum = 0;
        for (int j = 0; j < k; j++) {
          if (i + j < nums.length) {
            sum += nums[i + j];
          }
        }
        ans = sum > ans ? sum : ans;
      }
    }

    System.out.println("ans: " + ans);

  }


}
