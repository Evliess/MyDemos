package com.my.service.interview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HJ13 {
  public static void main(String[] args) throws IOException {
    BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
    String[] input = r.readLine().split(" ");
    StringBuilder sb = new StringBuilder();
    for (int i = input.length-1; i>=0 ;i--) {
      sb.append(input[i]).append(" ");
    }
    System.out.println(sb.substring(0, sb.length()-1));

  }
}
