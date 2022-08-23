package com.my.service.dp.decorator;

public class HtmlEncodeMessage implements Message{

  private Message msg;

  public HtmlEncodeMessage(Message msg) {
    this.msg = msg;
  }
  @Override
  public String getContent() {
    return "HtmlEncode" + msg.getContent();
  }
}
