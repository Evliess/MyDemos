package com.my.service.dp.respchain;

public interface LeaveApprover {
  void processLeaveApplication(LeaveApplication leaveApplication);
  String getApproverRole();
}
