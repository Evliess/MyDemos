package com.my.service.dp;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrategyTest {
  private final Logger logger = LoggerFactory.getLogger(StrategyTest.class);


  @Test
  public void test() {
    MySort mySort = new MySort(new MergeSort());
    logger.info(mySort.sort(""));

    mySort = new MySort(new QuickSort());
    logger.info(mySort.sort(""));
  }

}


abstract class Strategy {
  public abstract String sort(String str);
}

class MergeSort extends Strategy {
  @Override
  public String sort(String str) {
    return "Merge sort";
  }
}

class QuickSort extends Strategy {
  @Override
  public String sort(String str) {
    return "Quick sort";
  }
}

class MySort {
  private Strategy strategy;

  public MySort(Strategy strategy) {
    this.strategy = strategy;
  }

  public String sort(String str) {
    return this.strategy.sort(str);
  }
}
