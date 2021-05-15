package com.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class SecurityController {
  private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);


  @GetMapping("/public/hello")
  public List<String> publicHello() {
    return Arrays.asList("Hello", "World", "from", "Public");
  }

  @GetMapping("/private/hello")
  public List<String> privateHello() {
    return Arrays.asList("Hello", "World", "from", "Private");
  }

  @GetMapping("/all/hello")
  public List<String> allHello() {
    return Arrays.asList("Hello", "World", "from", "All");
  }

}
