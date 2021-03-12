package com.my.service.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HJ14 {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
      int num = sc.nextInt();
      List<String> strs = new ArrayList<>();
      for (int i = 0; i < num; i++) {
        strs.add(sc.next()); //nextLine() he next()
      }
      System.out.println(strs.get(num-1));
      strs.stream().sorted((str1, str2) -> str1.compareTo(str2)).forEach(str -> {
        System.out.println(str);
      });

  }
}
