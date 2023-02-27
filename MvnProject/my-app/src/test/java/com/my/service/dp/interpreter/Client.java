package com.my.service.dp.interpreter;

public class Client {
  public static void main(String[] args) {

    Report report =new Report("Metrics report", "ADMIN");

    ExpressBuilder builder = new ExpressBuilder();
    PermissionExpression exp = builder.build(report);
    System.out.println(exp);

    User user = new User("Dave", "USER", "ADMIN");
    System.out.println("User access report: "+ exp.interpret(user));


  }
}
