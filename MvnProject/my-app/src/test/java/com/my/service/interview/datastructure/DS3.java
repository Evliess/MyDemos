package com.my.service.interview.datastructure;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://leetcode.cn/problems/two-sum/
 */
public class DS3 {
  public int[] twoSum(int[] nums, int target) {
    int[] ans = new int[2];
    outer:
    for (int i = 0; i < nums.length; i++) {
      for (int j = 0; j < nums.length; j++) {
        if (nums[i] + nums[j] == target && i != j) {
          ans[0] = i;
          ans[1] = j;
          break outer;
        }
      }
    }
    return ans;
  }

  @Test
  public void fun() {
    int nums[] = {3, 2, 4};
    int target = 6;
    int ans[] = twoSum(nums, target);
    System.out.println(ans[0] + ":" + ans[1]);
  }


  public int[] twoSum_1(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap();
    for (int i=0; i< nums.length; i++) {
      if(map.containsKey(target - nums[i])){
        return new int[] {map.get(target - nums[i]), i};
      }
      map.put(nums[i], i);
    }
    return new int[0];
  }

  @Test
  public void fun1() {
    int nums[] = {3, 2, 4};
    int target = 6;
    int ans[] = twoSum_1(nums, target);
    System.out.println(ans[0] + ":" + ans[1]);
  }


}
