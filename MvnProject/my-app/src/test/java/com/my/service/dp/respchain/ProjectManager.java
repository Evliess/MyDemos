package com.my.service.dp.respchain;

public class ProjectManager extends Employee{
  public ProjectManager(LeaveApprover successor) {
    super("Project Manager", successor);
  }

  @Override
  protected boolean processRequest(LeaveApplication leaveApplication) {
    if(leaveApplication.getType() == LeaveApplication.Type.Sick) {
      if(leaveApplication.getNoOfDays() >=2) {
        leaveApplication.approve(getApproverRole());
      }
    }
    return false;
  }
}
