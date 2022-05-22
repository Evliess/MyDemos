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


}
