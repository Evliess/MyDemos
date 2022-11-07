package com.my.service.dp.command;

public class AddMemberCommand implements Command{

  private String emailAddress;
  private String listName;

  private EWSService receiver;

  public AddMemberCommand(String emailAddress, String listName, EWSService ewsService) {
    this.emailAddress = emailAddress;
    this.listName = listName;
    this.receiver = ewsService;
  }

  @Override
  public void execute() {
    receiver.addMember(emailAddress, listName);
  }
}
