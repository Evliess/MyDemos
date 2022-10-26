package com.my.service.dp.respchain;

public class Director extends Employee{
  public Director( LeaveApprover successor) {
    super("Director", successor);
  }

  @Override
  protected boolean processRequest(LeaveApplication leaveApplication) {
    switch (leaveApplication.getType()) {
      case Sick:
        leaveApplication.approve(getApproverRole());
        return true;
      case PTO:
        if(leaveApplication.getNoOfDays() <=5) {
          leaveApplication.approve(getApproverRole());
          return true;
        }
    }
    return false;
  }
}
