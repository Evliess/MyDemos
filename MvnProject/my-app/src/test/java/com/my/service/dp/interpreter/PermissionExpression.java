package com.my.service.dp.interpreter;

public interface PermissionExpression {
  boolean interpret(User user);
}
