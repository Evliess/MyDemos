package com.my.service.dp.interpreter;

public class NotExpression implements PermissionExpression{
  private PermissionExpression permission;

  public NotExpression(PermissionExpression permission) {
    this.permission = permission;
  }

  @Override
  public boolean interpret(User user) {
    return !permission.interpret(user);
  }
}
