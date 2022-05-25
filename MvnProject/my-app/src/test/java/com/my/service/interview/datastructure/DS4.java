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
      while (prePos >= 0 && res[prePos] > res[curPos]) {
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


  public void merge_1(int[] nums1, int m, int[] nums2, int n) {
    for (int j = 0; j < n; j++) {
      nums1[m + j] = nums2[j];
    }
    for (int k = 1; k < m + n; k++) {
      int prePos = k - 1;
      int curPos = k;
      int tmp;
      while (prePos >= 0 && nums1[prePos] > nums1[curPos]) {
        tmp = nums1[prePos];
        nums1[prePos] = nums1[curPos];
        nums1[curPos] = tmp;
        prePos--;
        curPos--;
      }
    }
  }

  @Test
  public void fun1() {
    int[] nums1 = {4, 5, 6, 0, 0, 0};
    int m = 3;
    int[] nums2 = {1, 2, 3};
    int n = 3;
    merge_1(nums1, m, nums2, n);
  }

  public void merge_2(int[] nums1, int m, int[] nums2, int n) {
    int p1 = 0, p2 = 0, curVal;
    int[] sorted = new int[m + n];
    while (p1 < m || p2 < n) {
      if (p1 == m) {
        curVal = nums2[p2];
        p2++;
      } else if (p2 == n) {
        curVal = nums1[p1];
        p1++;
      } else if (nums1[p1] < nums2[p2]) {
        curVal = nums1[p1];
        p1++;
      } else {
        curVal = nums2[p2];
        p2++;
      }
      sorted[p1 + p2 - 1] = curVal;
    }
    for (int s : sorted) {
      System.out.println(s);
    }
  }

  @Test
  public void fun2() {
    int[] nums1 = {4, 5, 6, 0, 0, 0};
    int m = 3;
    int[] nums2 = {1, 2, 3};
    int n = 3;
    merge_2(nums1, m, nums2, n);
  }


  private int getBasePos(int[] array, int left, int right) {
    int key = array[left];
    int i = left;
    int j = right;
    int tmp;
    while (i < j) {
      //find the ele < key
      while (i < j && array[j] >= key) {
        j--;
      }
      //find the ele > key
      while (i < j && array[i] <= key) {
        i++;
      }
      if (i < j) {
        tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
      }
    }
    if (i == j) {
      tmp = array[left];
      array[left] = array[i];
      array[i] = tmp;
    }
    return i;
  }

  public void quicksort(int[] array, int left, int right) {
    if (left > right)
      return;
    int base = getBasePos(array, left, right);
    quicksort(array, left, base-1);
    quicksort(array, base + 1, right);
  }

  public void merge_1_1(int[] nums1, int m, int[] nums2, int n) {
    for (int j = 0; j < n; j++) {
      nums1[m + j] = nums2[j];
    }
    quicksort(nums1, 0, m + n - 1);
    for (int ans : nums1) {
      System.out.println(ans);
    }
  }

  @Test
  public void fun3() {
    int[] nums1 = {4, 5, 6, 0, 0, 0};
    int m = 3;
    int[] nums2 = {1, 2, 7};
    int n = 3;
    merge_1_1(nums1, m, nums2, n);
  }


}
