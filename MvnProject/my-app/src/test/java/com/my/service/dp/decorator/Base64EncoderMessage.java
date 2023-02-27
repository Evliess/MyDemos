package com.my.service.dp.decorator;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64EncoderMessage implements Message {

  private Message msg;

  public Base64EncoderMessage(Message msg) {
    this.msg = msg;
  }

  @Override
  public String getContent() {
    return Base64.getEncoder().encodeToString(this.msg.getContent().getBytes(StandardCharsets.UTF_8));
  }
}
