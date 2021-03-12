package com.my.service.interview;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 写出一个程序，接受一个由字母、数字和空格组成的字符串，和一个字母，然后输出输入字符串中该字母的出现次数。不区分大小写。
 */
public class HJ2 {

  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    char[] arr = bufferedReader.readLine().toLowerCase().toCharArray();
    char[] target = bufferedReader.readLine().toLowerCase().toCharArray();
    int len = 0;
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] == target[0]) {
        len++;
      }
    }
    System.out.println(len);
  }
}
