package com.my.service.interview;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 计算字符串最后一个单词的长度，单词以空格隔开。
 */
public class HJ1 {

  public static void main(String[] args) throws Exception {
    fun2();
  }

  public static void fun2() throws Exception{
    int len = 0;
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    char c = (char) bufferedReader.read();
    while (c != '\n') {
      if (c != ' ') {
        len++;
      } else {
        len = 0;
      }
      c = (char) bufferedReader.read();
    }
    System.out.println(len);
  }

  public static void fun1() throws Exception{
    int len = 0;
    InputStream in = System.in;
    char c = (char) in.read();
    while (c != '\n') {
      if (c != ' ') {
        len++;
      } else {
        len = 0;
      }
      c = (char) in.read();
    }
    System.out.println(len);
  }


}
