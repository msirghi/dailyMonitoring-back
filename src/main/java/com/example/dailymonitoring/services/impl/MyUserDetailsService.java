package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.configs.utils.CustomUserDetails;
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
    return userRepository
        .getUserByUsername(username)
        .map(user -> new CustomUserDetails(user.getEmail(), user.getUsername(), user.getPassword(),
           true, new ArrayList<>(), user.getId()))
        .orElse(null);
  }
}
