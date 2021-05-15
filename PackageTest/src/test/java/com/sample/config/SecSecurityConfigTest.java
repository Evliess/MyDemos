package com.sample.config;


import com.sample.controller.TestBase;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SecSecurityConfigTest extends TestBase {
  private static final Logger logger = LoggerFactory.getLogger(SecSecurityConfigTest.class);

  @Autowired
  private WebApplicationContext context;

  private MockMvc mvc;

  @Before
  public void setup() {
    mvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(springSecurity())
        .build();
  }


  @WithMockUser(username = "user", password = "password", roles = "User")
  @Test
  public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
    mockMvc.perform(get("/private/hello").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }


  @Test
  public void givenAuthRequestOnPrivateService_shouldFailedWith401() throws Exception {
    this.mockMvc.perform(get("/private/hello").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }

  @Test
  public void givenAuthRequestOnPublicService_shouldSucceedWith200() throws Exception {
    this.mockMvc.perform(get("/public/hello").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
}
