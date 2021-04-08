package com.my.service.interview;

public class KMP {

  //aaabbbbccccded
  //ccde

  public static void main(String[] args) {
    String source = "asaccccdedasd";
    String target = "ccdedasd";

    System.out.println(strMatch_1(source.toCharArray(), target.toCharArray()));
  }



  public static int strMatch_1(char[] source, char[] target) {
    int i = 0, j = 0;
    int sourceLength = source.length;
    int targetLength = target.length;
    while (i < sourceLength && j < targetLength) {
      if (source[i] == target[j]) {
        if (j == targetLength - 1) {
          return i - j;
        } else {
          i++;
          j++;
        }
      } else {
        i = i - j + 1;
        j = 0;
      }
    }
    return -1;
  }
}
