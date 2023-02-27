package com.my.service.dp.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class User {
  private List<String> permissions;
  private String username;

  public User(String username, String... permissions) {
    this.permissions = new ArrayList<>();
    Stream.of(permissions).forEach(e-> this.permissions.add(e.toLowerCase(Locale.ROOT)));
    this.username = username;
  }

  public List<String> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<String> permissions) {
    this.permissions = permissions;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
