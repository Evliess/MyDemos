package com.my.service.dp.command;

public class Client {
  public static void main(String[] args) throws InterruptedException {
    EWSService service = new EWSService();
    Command c1 = new AddMemberCommand("1@1", "test", service);
    MailTasksRunner.getInstance().addCommand(c1);

    Command c2 = new AddMemberCommand("2@2", "test", service);
    MailTasksRunner.getInstance().addCommand(c2);

    Thread.sleep(3000);

    MailTasksRunner.getInstance().shutdown();
  }
}
