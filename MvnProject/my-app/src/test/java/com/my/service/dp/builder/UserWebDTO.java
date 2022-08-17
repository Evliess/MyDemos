package com.my.service.dp.builder;

public class UserWebDTO implements UserDTO {
  private String name;
  private String address;
  private String age;

  public UserWebDTO(String name, String address, String age) {
    this.name = name;
    this.address = address;
    this.age = age;
  }

  @Override
  public String toString() {
    return "UserWebDTO{" +
        "name='" + name + '\'' +
        ", address='" + address + '\'' +
        ", age='" + age + '\'' +
        '}';
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public String getAge() {
    return age;
  }
}
