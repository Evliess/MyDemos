package com.my.service.dp.decorator;

public class Client {
  public static void main(String[] args) {
    Message message = new TextMessage("The world is big!");
    System.out.println(message.getContent());

    Message decorator = new HtmlEncodeMessage(message);
    System.out.println(decorator.getContent());

    Message base64 = new Base64EncoderMessage(decorator);
    System.out.println(base64.getContent());
  }
}
