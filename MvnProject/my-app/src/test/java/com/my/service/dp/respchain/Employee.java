package com.my.service.dp.respchain;

public abstract class Employee implements LeaveApprover{
  private String role;
  private LeaveApprover successor;
  public Employee(String role, LeaveApprover successor) {
    this.role = role;
    this.successor = successor;
  }
  public void processLeaveApplication(LeaveApplication leaveApplication){
    if(!processRequest(leaveApplication) && successor!=null) {
      successor.processLeaveApplication(leaveApplication);
    }
  }

  protected abstract boolean processRequest(LeaveApplication leaveApplication);
  public String getApproverRole(){
    return this.role;
  }

}
