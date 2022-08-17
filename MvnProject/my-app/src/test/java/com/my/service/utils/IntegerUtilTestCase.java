package com.my.service.utils;

import org.junit.Test;

public class IntegerUtilTestCase {

  /** high-16 bit -- read
   *  low-16 bit -- write
   * 0|0|0|0|0|0|0|0|0|0|0|0|0|0|1|0#0|0|0|0|0|0|0|0|0|0|0|0|0|0|1|1
   */
  @Test
  public void read() {
    int state = 2;
    // state = write + 1
    state = state + 1;
    // write = 7
    System.out.println("2 radix: " + Integer.toString(state, 2));
    System.out.println("10 radix: " + state);
    //state = read + 1
    state = state + (1 << 16);
    state = state + (1 << 16);
    //read = 1
    System.out.println("2 radix: " + Integer.toString(state, 2));
    System.out.println("10 radix: " + state);
    // get write
    // int write = state & ((1 << 16) - 1);
    int write = state % (1<<16);
    System.out.println("write value: " + write);
    // get read
    int read = state >> 16;
    System.out.println("read value: " + read);
  }
}
