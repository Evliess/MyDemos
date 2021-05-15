package com.sample.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class GreetingControllerTest extends TestBase {

  @InjectMocks
  private GreetingController greetingController;

  @Before
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    this.mockMvc = MockMvcBuilders.standaloneSetup(greetingController).build();
  }

  @Test
  public void greeting() throws Exception {
    mockMvc.perform(get("/greeting")).andExpect(status().is2xxSuccessful());
  }
}
