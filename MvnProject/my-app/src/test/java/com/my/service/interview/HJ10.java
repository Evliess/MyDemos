package com.my.service.interview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class HJ10 {
  public static void main(String[] args) throws IOException {
    BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
    char[] arr = r.readLine().toCharArray();
    boolean[] flag = new boolean[128];
    int count = 0;
    for (char c: arr) {
      if(!flag[c]) {
        count ++;
        flag[c] = true;
      }
    }
    System.out.println(count);
  }


  public static void fun1() {
    Scanner sc = new Scanner(System.in);
    while (sc.hasNext()) {
      char[] arr = sc.nextLine().toCharArray();
      Set<Character> sets = new HashSet<>();
      for (int i = 0; i < arr.length; i++) {
        if (0 < arr[i] && arr[i] < 128) {
          sets.add(arr[i]);
        }
      }
      System.out.println(sets.size());
    }
  }
}
