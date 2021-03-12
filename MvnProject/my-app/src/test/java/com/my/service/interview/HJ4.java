package com.my.service.interview;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *  •连续输入字符串，请按长度为8拆分每个字符串后输出到新的字符串数组；
 * •长度不是8整数倍的字符串请在后面补数字0，空字符串不处理。
 */
public class HJ4 {

  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    String str;
    while ((str = bufferedReader.readLine()) != null) {
      if (str.length() % 8 !=0) {
        str = str + "00000000";
      }
      while (str.length() >= 8) {
        System.out.println(str.substring(0, 8));
        str = str.substring(8);
      }



    }

  }
}
