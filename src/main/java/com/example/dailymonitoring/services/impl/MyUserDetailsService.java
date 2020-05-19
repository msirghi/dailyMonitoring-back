package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.configs.utils.CustomUserDetails;
import com.example.dailymonitoring.models.entities.RoleEntity;
import com.example.dailymonitoring.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

  private UserRepository userRepository;

  public MyUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
        .getUserByUsername(username)
        .map(user -> new CustomUserDetails(
            user.getEmail(),
            user.getUsername(),
            user.getPassword(),
            true,
            new ArrayList<>(),
            user.getId()))
        .orElse(new CustomUserDetails(
            "",
            "",
            "",
            true,
            new ArrayList<>(),
            0L));
  }
//       .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));
}
