package com.my.service.dp.prototype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {
  private static final Logger logger = LoggerFactory.getLogger(Client.class);

  public static void main(String[] args) throws CloneNotSupportedException {
    Swordsman swordsman = new Swordsman();
    swordsman.action();
    logger.info("{}", swordsman);

    Swordsman swordsman1 = (Swordsman) swordsman.clone();
    logger.info("{}", swordsman1);


  }
}
