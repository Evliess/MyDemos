package com.my.service.dp.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.FileAlreadyExistsException;
import java.time.LocalDate;

//Sample Code
//java.util.Calendar.Builder
public class Client {
  private static final Logger logger = LoggerFactory.getLogger(Client.class);

  public static void main(String[] args) {
    User user = createUser();
    UserDTOBuilder builder = new UserWebDTOBuilder();
    UserDTO userDTO = director(builder, user);
    logger.info("{}", userDTO);


  }

  private static UserDTO director(UserDTOBuilder builder, User user) {
    return builder
        .withAddress(user.getAddress())
        .withFirstName(user.getFirstName()).withLastName(user.getLastName())
        .withBirthday(user.getBirthday()).build();
  }

  private static User createUser() {
    User user = new User();
    user.setBirthday(LocalDate.of(1989, 12, 18));
    user.setFirstName("Xi");
    user.setLastName("Yi");
    Address address = new Address();
    address.setCity("NY");
    address.setHouseNumber("888");
    address.setState("WC");
    address.setZipcode("09807");
    address.setStreet("AAA");
    user.setAddress(address);
    return user;
  }
}
