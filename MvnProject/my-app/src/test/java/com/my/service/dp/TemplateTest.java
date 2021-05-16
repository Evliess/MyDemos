package com.my.service.dp;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateTest {
  private final Logger logger = LoggerFactory.getLogger(TemplateTest.class);

  @Test
  public void test() {
    new PlayGame().play();
    new PlayMusic().play();
  }


}


abstract class Template {
  abstract void step1();

  abstract void step2();

  void play() {
    step1();
    step2();
  }
}

class PlayMusic extends Template {
  private final Logger logger = LoggerFactory.getLogger(PlayMusic.class);

  @Override
  void step1() {
    logger.info("Play Music Step1");
  }

  @Override
  void step2() {
    logger.info("Play Music Step2");
  }
}

class PlayGame extends Template {
  private final Logger logger = LoggerFactory.getLogger(PlayGame.class);

  @Override
  void step1() {
    logger.info("Play Game Step1");
  }

  @Override
  void step2() {
    logger.info("Play Game Step2");
  }
}
