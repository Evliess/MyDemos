package com.my.service.interview.datastructure;

import org.junit.Test;

/**
 * https://leetcode.cn/problems/merge-sorted-array/
 */
public class DS4 {
  public void merge(int[] nums1, int m, int[] nums2, int n) {
    int[] res = new int[m + n];
    for (int i = 0; i < m; i++) {
      res[i] = nums1[i];
    }

    for (int j = 0; j < n; j++) {
      res[m + j] = nums2[j];
    }

    for (int k = 1; k < res.length; k++) {
      int prePos = k - 1;
      int curPos = k;
        while (prePos >= 0 && res[prePos] > res[curPos] ) {
          int tmp = res[curPos];
          res[curPos] = res[prePos];
          res[prePos] = tmp;
          prePos--;
          curPos--;
        }

    }

    for (int l = 0; l < res.length; l++) {
      nums1[l] = res[l];
    }

    for (int s : res) {
      System.out.println(s);
    }

  }

  /**
   * 4,5,6,1,2,3
   * 4,5,1,6,2,3
   */
  @Test
  public void fun() {
    int[] nums1 = {4, 5, 6, 0, 0, 0};
    int m = 3;
    int[] nums2 = {1, 2, 3};
    int n = 3;
    merge(nums1, m, nums2, n);

  }
}
