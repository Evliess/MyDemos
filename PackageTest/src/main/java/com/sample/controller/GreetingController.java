package com.sample.controller;

import com.sample.model.Greeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;


@RestController
public class GreetingController {

  private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);
  private static final String template = "Hello,Spring %s!";
  private final AtomicLong counter = new AtomicLong();

  @GetMapping("/greeting")
  public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
    logger.info("debug log!");
    return new Greeting(counter.incrementAndGet(), String.format(template, name));
  }


}
