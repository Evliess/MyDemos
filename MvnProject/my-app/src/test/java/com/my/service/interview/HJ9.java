package com.my.service.interview;

import java.util.Scanner;

public class HJ9 {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    while (sc.hasNext()) {
      char[] num = sc.nextLine().toCharArray();
      StringBuilder sb = new StringBuilder("");
      for (int i = num.length - 1; i >= 0; i--) {
        String str = num[i]+"";
        if (!sb.toString().contains(str)) {
          sb.append(str);
          System.out.print(str);
        }
      }

    }
  }
}
