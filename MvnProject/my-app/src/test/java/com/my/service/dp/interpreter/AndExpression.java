package com.my.service.dp.interpreter;

public class AndExpression implements PermissionExpression{

  private PermissionExpression permission1;
  private PermissionExpression permission2;

  public AndExpression(PermissionExpression permission1, PermissionExpression permission2) {
    this.permission1 = permission1;
    this.permission2 = permission2;
  }

  @Override
  public boolean interpret(User user) {
    return permission1.interpret(user) && permission2.interpret(user);
  }
}
