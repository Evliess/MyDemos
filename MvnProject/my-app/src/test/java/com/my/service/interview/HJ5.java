package com.my.service.interview;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HJ5 {

  public static void main(String[] args) throws Exception {
    BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
    String str;
    while ((str = r.readLine()) != null) {
      System.out.println(Long.parseLong(str.substring(2), 16));
    }
  }
}
