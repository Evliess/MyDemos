package com.my.service.interview;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class HJ8 {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    while (sc.hasNext()) {
      int num = sc.nextInt();
      Map<Integer, Integer> map = new TreeMap<>();
      for (int i =0; i< num; i++) {
        int key = sc.nextInt();
        int value = sc.nextInt();
        if (map.containsKey(key)) {
          map.put(key, value + map.get(key));
        } else {
          map.put(key, value);
        }
      }
      for (Integer key: map.keySet()){
        System.out.println(key + " " + map.get(key));
      }
    }
  }
}
