package com.sample.service;

import com.sample.controller.GreetingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);


  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    if ("spring".equals(userName)) {
      return new User("spring", passwordEncoder.encode("pass"), AuthorityUtils.createAuthorityList("admin", "user"));
    }
    return null;
  }
}
