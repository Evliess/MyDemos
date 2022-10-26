package com.my.service.dp.respchain;

import java.time.LocalDate;

public class Client {
  public static void main(String[] args) {
    LeaveApplication application = LeaveApplication.getBuilder().withType(LeaveApplication.Type.Sick)
        .from(LocalDate.now()).to(LocalDate.now().plusDays(3))
        .build();
    System.out.println(application);
    LeaveApprover approver = createChain();
    approver.processLeaveApplication(application);
    System.out.println(application);

  }

  private static LeaveApprover createChain() {
    Boss boss = new Boss(null);
    Director director = new Director(boss);
    ProjectManager projectManager = new ProjectManager(director);
    return projectManager;
  }
}
