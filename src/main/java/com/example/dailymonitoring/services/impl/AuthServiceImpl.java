package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.configs.utils.JwtUtil;
import com.example.dailymonitoring.exceptions.BadRequestException;
import com.example.dailymonitoring.exceptions.ForbiddenException;
import com.example.dailymonitoring.models.auth.AuthenticationRequestData;
import com.example.dailymonitoring.models.auth.AuthenticationResponseData;
import com.example.dailymonitoring.models.entities.UserEntity;
import com.example.dailymonitoring.respositories.UserRepository;
import com.example.dailymonitoring.services.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Sirghi Mihail
 */
@Service
public class AuthServiceImpl implements AuthService {

  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;

  public AuthServiceImpl(JwtUtil jwtUtil,
                         UserRepository userRepository,
                         AuthenticationManager authenticationManager,
                         UserDetailsService userDetailsService) {
    this.jwtUtil = jwtUtil;
    this.userRepository = userRepository;
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
  }

  @Override
  public AuthenticationResponseData authenticate(AuthenticationRequestData authenticationRequestData) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authenticationRequestData.getUsername(),
              authenticationRequestData.getPassword())
      );
    } catch (BadCredentialsException e) {
      throw new BadRequestException("Invalid username or password.");
    }

    UserDetails userDetails = userDetailsService
        .loadUserByUsername(authenticationRequestData.getUsername());
    final String jwt = jwtUtil.generateToken(userDetails);
    final String refreshToken = jwtUtil
        .createRefreshToken(authenticationRequestData.getUsername(), userDetails.getPassword());

    return AuthenticationResponseData
        .builder()
        .jwt(jwt)
        .refreshToken(refreshToken)
        .build();
  }

  @Override
  public AuthenticationResponseData renewToken(AuthenticationResponseData authenticationResponseData) {
    Object userId = jwtUtil.getIdFromRefreshToken(authenticationResponseData.getRefreshToken());

    UserEntity userEntity = userRepository.getActiveUser(Long.parseLong(String.valueOf(userId)))
        .orElseThrow(ForbiddenException::new);
    String username = jwtUtil.extractUsernameFromRefreshToken(authenticationResponseData.getRefreshToken(),
        userEntity.getPassword());

    UserDetails userDetails = userDetailsService.loadUserByUsername(userEntity.getUsername());
    String refreshToken = authenticationResponseData.getRefreshToken();

    String token = jwtUtil.generateToken(userDetails);
    Date expiration = (Date) jwtUtil.getExpFromRefreshToken(refreshToken);

    if (expiration.before(new Date())) {
      refreshToken = jwtUtil.createRefreshToken(username, userDetails.getPassword());
    }

    return AuthenticationResponseData
        .builder()
        .jwt(token)
        .refreshToken(refreshToken)
        .build();
  }
}
