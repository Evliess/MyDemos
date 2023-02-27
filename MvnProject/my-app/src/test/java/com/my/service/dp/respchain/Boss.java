package com.my.service.dp.respchain;

public class Boss extends Employee{

  public Boss(LeaveApprover successor) {
    super("Boss", successor);
  }

  @Override
  protected boolean processRequest(LeaveApplication leaveApplication) {
    if(leaveApplication.getType() == LeaveApplication.Type.PTO) {
      leaveApplication.approve(getApproverRole());
      return true;
    }
    return false;
  }
}
