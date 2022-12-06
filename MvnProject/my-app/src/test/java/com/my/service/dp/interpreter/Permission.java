package com.my.service.dp.interpreter;

import java.util.Locale;

public class Permission implements PermissionExpression{

  private String permission;

  public Permission(String permission) {
    this.permission = permission.toLowerCase(Locale.ROOT);
  }

  @Override
  public boolean interpret(User user) {
    return user.getPermissions().contains(permission);
  }

  @Override
  public String toString() {
    return "Permission{" +
        "permission='" + permission + '\'' +
        '}';
  }
}
