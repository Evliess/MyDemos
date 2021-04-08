package com.my.service.understanding.jvm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CountWords {

  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    int[] array = new int[n];
    for (int i = 0; i < n; i++) {
      int input = sc.nextInt();
      array[i] = input;
    }

    List<Integer> temp = new ArrayList<>();

    for (int i = 0; i < array.length; i++) {
      if (!temp.contains(array[i])) {
        temp.add(array[i]);
      }
    }

    List<Integer> sorted = temp.stream().sorted().collect(Collectors.toList());
    int m = sc.nextInt();

    List<Integer> listMax = new ArrayList<>();
    List<Integer> listMin = new ArrayList<>();

    for (int i = 0; i < m; i++) {
      listMin.add(sorted.get(i));
      listMax.add(sorted.get(sorted.size() - i - 1));
    }

    for (Integer integer : listMax) {
      if (listMin.contains(integer)) {
        System.out.println(-1);
        return;
      }
    }

    listMax.addAll(listMin);

    int sum = 0;

    for (Integer integer : listMax) {
      sum = sum + integer;
    }
    System.out.println(sum);

  }



  public static void fun1() throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String str = br.readLine();
    int start = Integer.valueOf(br.readLine());
    int end = Integer.valueOf(br.readLine());
    StringBuilder sb = new StringBuilder();
    String[] array = str.split("\\s");
    for (int i = 0; i < array.length; i++) {
      if (array[i].trim().length() != 0) {
        sb.append(array[i]).append(",");
      }
    }
    sb.setLength(sb.length() - 1);
    array = sb.toString().split(",");
    StringBuilder res = new StringBuilder();
    if (end > array.length) {
      System.out.println("EMPTY");
      return;
    }
    for (int i = 0; i < start; i++) {
      res.append(array[i]).append(" ");
    }


    for (int i = end; i >= start; i--) {
      res.append(array[i]).append(" ");
    }

    for (int i = end + 1; i < array.length; i++) {
      res.append(array[i]).append(" ");
    }
    System.out.println(res.toString().trim());
  }
}
