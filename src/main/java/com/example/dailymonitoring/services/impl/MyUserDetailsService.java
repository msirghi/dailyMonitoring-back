package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.respositories.UserRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (username.equalsIgnoreCase("foo")) {
      return new User("foo",
          passwordEncoder.encode("foo"),
          new ArrayList<>());
    }

    return userRepository
        .findUserByUsername(username)
        .map(user -> new User(user.getUsername(), user.getPassword(), new ArrayList<>()))
        .orElse(null);
  }
}
