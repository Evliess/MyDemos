package com.my.service.interview.datastructure;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * https://leetcode.cn/problems/contains-duplicate/
 */
public class DS1 {

  public boolean containsDuplicate(int[] nums) {
    Set set = new HashSet();
    for ( int n: nums) {
      set.add(n);
    }
    return nums.length!=set.size();
  }

  @Test
  public void fun() {
    int[] nums = {1, 2, 3, 4, 1};
    System.out.println(containsDuplicate(nums));
  }
}
