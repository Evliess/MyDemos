package com.my.service.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * https://www.nowcoder.com/practice/196534628ca6490ebce2e336b47b3607?tpId=37&tqId=21229&rp=1&ru=%2Fta%2Fhuawei&qru=%2Fta%2Fhuawei%2Fquestion-ranking&tab=answerKey
 */
public class HJ6_1 {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Integer num = scanner.nextInt();
    List<Integer> result = new ArrayList<>();

    for (int i = 2; i <= Math.sqrt(num); i++) {
      if (num % i == 0) {
        result.add(i);
        num = num / i;
        i--;
      }
    }

    result.forEach(n -> System.out.print(n + " "));
  }
}
