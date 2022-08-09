package com.my.service.dp.builder.inner;

import com.my.service.dp.builder.Address;

import java.time.LocalDate;
import java.time.Period;

public class UserDTO {
  private String name;
  private String address;
  private String age;

  public UserDTO(String name, String address, String age) {
    this.name = name;
    this.address = address;
    this.age = age;
  }

  @Override
  public String toString() {
    return "UserDTO{" +
        "name='" + name + '\'' +
        ", address='" + address + '\'' +
        ", age='" + age + '\'' +
        '}';
  }

  public String getName() {
    return name;
  }

  private void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  private void setAddress(String address) {
    this.address = address;
  }

  public String getAge() {
    return age;
  }

  private void setAge(String age) {
    this.age = age;
  }

  public UserDTOBuilder getBuilder() {
    return new UserDTOBuilder();
  }

  public static class UserDTOBuilder {
    private String firstName;
    private String lastName;
    private String age;
    private String address;

    public UserDTOBuilder withFirstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public UserDTOBuilder withLastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public UserDTOBuilder withBirthday(LocalDate date) {
      Period ageInYears = Period.between(date, LocalDate.now());
      age = String.valueOf(ageInYears.getYears());
      return this;
    }

    public UserDTOBuilder withAddress(Address address) {
      this.address = address.getHouseNumber() + ", " + address.getStreet()
          + "\n" + address.getCity() + "\n" + address.getState() + "\n" + address.getZipcode();
      return this;
    }

    public UserDTO build() {
      return new UserDTO(firstName + " " + lastName, address, age);
    }
  }
}
