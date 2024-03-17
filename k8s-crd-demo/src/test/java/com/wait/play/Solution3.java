package com.wait.play;

/**
 * There are N patients (numbered from 0 to N-1) who want to visit the doctor. The doctor has S possible appointment slots, numbered
 * from 1 to S. Each of the patients has two preferences. Patient K would like to visit the doctor during either slot A[K] or slot B[K]. The
 * doctor can treat only one patient during each slot.
 * Is it possible to assign every patient to one of their preferred slots so that there will be at most one patient assigned to each slot?
 * Write a function:
 * class Solution { public boolean solution (int[] A, int[] B, int S); }
 * that, given two arrays A and B, both of N integers, and an integer S, returns true if it is possible to assign every patient to one of their
 * preferred slots, one patient to one slot, and false otherwise.
 * Examples:
 * 1. Given A = [1, 1, 3], B = [2, 2, 1] and S = 3, the function should return true. We could assign patients in the following way: [1, 2, 3], where
 * the K-th element of the array represents the number of the slot which patient K was assigned. Another correct assignment would be [2,
 * 1,3]. On the other hand, [2, 2, 1] would be an incorrect assignment as two patients would be assigned to slot 2.
 * 2. Given A = [3, 2, 3, 1], B = [1, 3, 1, 2] and S = 3, the function should return false. There are only three slots available, but there are four
 * patients who want to visit the doctor. It is therefore not possible to assign the patients to the slots so that only one patient at a time
 * would visit the doctor.
 * 3. Given A = [2, 5, 6, 5], B = [5, 4, 2, 2] and S = 8, the function should return true. For example, we could assign patients in the following
 * way: [5, 4, 6, 2].
 * 4. Given A = [1, 2, 1, 6, 8, 7, 8], B = [2, 3, 4, 7, 7, 8, 7] and S=10, the function should return false. It is not possible to assign all of the
 * patients to one of their preferred slots so that only one patient will visit the doctor during one slot.
 * Write an efficient algorithm for the following assumptions:
 * • N is an integer within the range [1..100,000];
 * • S is an integer within the range [2..100,000];
 * • each element of arrays A and B is an integer within the range [1..S];
 * • no patient has two preferrences for the same slot, i.e. A[i] + B[i].
 */


import java.util.PriorityQueue;

/**
 * 有 N 名患者（编号从 0 到 N-1）想要去看医生。医生有 S 个可能的预约时段，已编号
 * 从 1 到 S。每个患者都有两个偏好。患者 K 希望在时段 A[K] 或时段 B[K] 期间去看医生。这
 * 医生在每个时段只能治疗一名患者。
 * 是否可以将每位患者分配到他们首选的时段之一，以便每个时段最多分配一名患者？
 * 写一个函数：
 * 类解决方案 { 公共布尔解决方案 (int[] A, int[] B, int S); }
 * 给定两个数组 A 和 B（均为 N 个整数）和一个整数 S，如果可以将每个患者分配给他们的其中一个，则返回 true
 * 首选槽位，一名患者对应一个槽位，否则为 false。
 * 例子：
 * 1. 给定 A = [1, 1, 3]、B = [2, 2, 1] 且 S = 3，该函数应返回 true。我们可以通过以下方式分配患者：[1,2,3]，其中
 * 数组的第 K 个元素表示分配给患者 K 的槽号。另一个正确的分配是 [2,
 * 1,3]。另一方面，[2, 2, 1] 将是不正确的分配，因为两名患者将被分配到槽 2。
 * 2. 给定 A = [3, 2, 3, 1]，B = [1, 3, 1, 2] 且 S = 3，该函数应返回 false。只有三个可用插槽，但有四个
 * 想要去看医生的患者。因此，不可能将患者分配到槽位，以便一次只能有一名患者
 * 会去看医生。
 * 3. 给定 A = [2, 5, 6, 5]，B = [5, 4, 2, 2] 且 S = 8，该函数应返回 true。例如，我们可以将患者分配如下
 * 方式：[5,4,6,2]。
 * 4. 给定 A = [1, 2, 1, 6, 8, 7, 8]，B = [2, 3, 4, 7, 7, 8, 7] 且 S=10，该函数应返回 false。不可能分配所有的
 * 患者可以选择他们喜欢的时段之一，这样在一个时段内只有一名患者去看医生。
 * 为以下假设编写一个有效的算法：
 * • N 是 [1..100,000] 范围内的整数；
 * • S 是[2..100,000]范围内的整数；
 * • 数组A 和B 的每个元素都是[1..S] 范围内的整数；
 * • 没有患者对同一时段有两个偏好，即A[i] + B[i]。
 */
public class Solution3 {
  public boolean solution(int[] A, int[] B, int S) {
    PriorityQueue<Integer>[] slots = new PriorityQueue[S + 1];
    for (int i = 1; i <= S; i++) {
      slots[i] = new PriorityQueue<>();
    }

    int N = A.length;
    for (int i = 0; i < N; i++) {
      slots[Math.min(A[i], B[i])].add(Math.max(A[i], B[i]));
    }

    boolean[] assigned = new boolean[S + 1];
    for (int i = 1; i <= S; i++) {
      while (!slots[i].isEmpty()) {
        int j = slots[i].poll();
        if (!assigned[i]) {
          assigned[i] = true;
        } else if (!assigned[j]) {
          assigned[j] = true;
        } else {
          return false;
        }
      }
    }

    return true;
  }
}
