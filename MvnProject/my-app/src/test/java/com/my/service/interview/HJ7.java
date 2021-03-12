package com.my.service.interview;

/**
 * 位运算
 * 正数的原码，反码，补码都一样
 * 负数的反码 符号位不变，其余各位取反
 * 负数的补码 反码+1
 */
public class HJ7 {
  public static void main(String[] args) {
    //1 0 0    0 1 0 0
    //0 1 1
    int a = -4;
    int b = 4;
    System.out.println(a & b);
    System.out.println(a | b);
    System.out.println(~a);
    //相同结果为0， 不同结果为1
    System.out.println(a ^ b);

  }


}
