package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.configs.utils.CustomUserDetails;
import com.example.dailymonitoring.respositories.UserRepository;
import java.util.ArrayList;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  private final BCryptPasswordEncoder passwordEncoder;

  public MyUserDetailsService(UserRepository userRepository,
      BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository
        .getUserByUsername(username)
        .map(user -> new CustomUserDetails(user.getEmail(), user.getUsername(), user.getPassword(),
           true, new ArrayList<>(), user.getId()))
        .orElse(null);
  }
}
