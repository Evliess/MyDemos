package com.my.service.dp.builder.inner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class Client {
  private static final Logger logger = LoggerFactory.getLogger(Client.class);

  public static void main(String[] args) {
    UserDTO.UserDTOBuilder builder = new UserDTO.UserDTOBuilder();
    UserDTO userDTO =
        builder.withFirstName("firstName").withLastName("lastName").
            withBirthday(LocalDate.of(1989, 03, 12)).build();
    logger.info("{}", userDTO);
  }


}
