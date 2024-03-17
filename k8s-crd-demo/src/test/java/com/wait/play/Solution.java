package com.wait.play;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * You are given a string letters made of N English letters. Count the number of different letters that appear in both uppercase and
 * lowercase where all lowercase occurrences of the given letter appear before any uppercase occurrence.
 * For example, for letters = "aaAbcCABBc" the answer is 2. The condition is met for letters 'a' and 'b', but not for 'c'.
 * Write a function:
 * class Solution { public int solution (String letters); }
 * that, given a string letters, returns the number of different letters fulfilling the conditions above.
 * Examples:
 * 1. Given letters = "aaAbcCABBc", the function should return 2, as explained above.
 * 2. Given letters = "xyzXYZabcABC", the function should return 6.
 * 3. Given letters = "ABCabcAefG", the function should return 0.
 * Write an efficient algorithm for the following assumptions:
 * • N is an integer within the range [1..100,000];
 * • string letters is made only of letters (a-z and/or A-Z).
 * <p>
 * You are given an array A of N integers, representing the maximum heights of N skyscrapers to be built.
 * Your task is to specify the actual heights of the skyscrapers, given that:
 * • the height of the K-th skyscraper should be positive and not bigger than A[K];
 * • no two skyscrapers should be of the same height;
 * • the total sum of the skyscrapers' heights should be the maximum possible.
 * Write a function:
 * class Solution { public int[] solution (int[] A); }
 * that, given an array A of N integers, returns an array B of N integers where B[K] is the assigned height of the K-th skyscraper satisfying the
 * above conditions.
 * If there are several possible answers, the function may return any of them. You may assume that it is always possible to build all
 * skyscrapers while fulfilling all the requirements.
 * Examples:
 * 1. Given A = [1, 2, 3], your function should return [1, 2, 3], as all of the skyscrapers may be built to their maximum height.
 * 2. Given A = [9, 4, 3, 7, 7], your function may return [9, 4, 3, 7, 6]. Note that [9, 4, 3, 6, 7] is also a valid answer. It is not possible for the last
 * two skyscrapers to have the same height. The height of one of them should be 7 and the other should be 6.
 * 3. Given A = [2, 5, 4, 5, 5], your function should return [1, 2, 3, 4, 5].
 * Write an efficient algorithm for the following assumptions:
 * • N is an integer within the range [1..50,000];
 * • each element of array A is an integer within the range [1..1,000,000,000];
 * • there is always a solution for the given input.
 */

/**
 * You are given an array A of N integers, representing the maximum heights of N skyscrapers to be built.
 * Your task is to specify the actual heights of the skyscrapers, given that:
 * • the height of the K-th skyscraper should be positive and not bigger than A[K];
 * • no two skyscrapers should be of the same height;
 * • the total sum of the skyscrapers' heights should be the maximum possible.
 * Write a function:
 * class Solution { public int[] solution (int[] A); }
 * that, given an array A of N integers, returns an array B of N integers where B[K] is the assigned height of the K-th skyscraper satisfying the
 * above conditions.
 * If there are several possible answers, the function may return any of them. You may assume that it is always possible to build all
 * skyscrapers while fulfilling all the requirements.
 * Examples:
 * 1. Given A = [1, 2, 3], your function should return [1, 2, 3], as all of the skyscrapers may be built to their maximum height.
 * 2. Given A = [9, 4, 3, 7, 7], your function may return [9, 4, 3, 7, 6]. Note that [9, 4, 3, 6, 7] is also a valid answer. It is not possible for the last
 * two skyscrapers to have the same height. The height of one of them should be 7 and the other should be 6.
 * 3. Given A = [2, 5, 4, 5, 5], your function should return [1, 2, 3, 4, 5].
 * Write an efficient algorithm for the following assumptions:
 * • N is an integer within the range [1..50,000];
 * • each element of array A is an integer within the range [1..1,000,000,000];
 * • there is always a solution for the given input.
 */

/**
 * 给你一个由 N 个英文字母组成的字符串字母。计算同时出现大写和大​​写的不同字母的数量
 * lowercase，其中给定字母的所有小写字母出现在任何大写字母出现之前。
 * 例如，对于字母 =“aaAbcCABBc”，答案为 2。字母“a”和“b”满足条件，但“c”不满足条件。
 * 写一个函数：
 * 类解决方案{ public int解决方案（字符串字母）; }
 * 给定一个字符串字母，返回满足上述条件的不同字母的数量。
 * 例子：
 * 1. 给定字母 = "aaAbcCABBc"，函数应返回 2，如上所述。
 * 2. 给定字母 = "xyzXYZabcABC"，函数应返回 6。
 * 3. 给定字母 = "ABCabcAefG"，函数应返回 0。
 * 为以下假设编写一个有效的算法：
 * • N 是 [1..100,000] 范围内的整数；
 * • 字符串字母仅由字母（a-z 和/或A-Z）组成。
 * 版权所有 2009-2023 Codility Limited。版权所有。禁止未经授权的复制、出版或披露。
 */

public class Solution {
  public static int[] solution(int[] A) {
    Set<Integer> set = new HashSet(Arrays.asList(A));
    if(A.length == set.size()) {
      return A;
    }
    int[] B = new int[A.length];
    set = new HashSet();
    for(int i=0; i<A.length; i++) {
      if(!set.contains(A[i])) {
        set.add(A[i]);
        B[i]=A[i];
      } else {
        int b = A[i];
        for (int j=1; j<b;j++) {
          int temp = A[i]-j;
          if(!set.contains(temp)) {
            set.add(temp);
            B[i]=temp;
            break;
          }
        }
      }
    }
    return B;
  }

  public static void main(String[] args) {
    int a[] = solution(new int[]{9,4,3,7,7});
    for (int i=0; i<a.length;i++ ) {
      System.out.println(a[i]);
    }
  }
}