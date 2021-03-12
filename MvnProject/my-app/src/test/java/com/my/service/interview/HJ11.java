package com.my.service.interview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HJ11 {
  public static void main(String[] args) throws IOException {
    BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder(r.readLine());
    System.out.println(sb.reverse());

  }
}
