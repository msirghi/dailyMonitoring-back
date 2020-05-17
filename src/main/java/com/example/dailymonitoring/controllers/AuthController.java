package com.example.dailymonitoring.controllers;

import com.example.dailymonitoring.configs.utils.JwtUtil;
import com.example.dailymonitoring.controllers.api.AuthApi;
import com.example.dailymonitoring.models.Error;
import com.example.dailymonitoring.models.auth.AuthenticationRequestData;
import com.example.dailymonitoring.models.auth.AuthenticationResponseData;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController implements AuthApi {

  private final AuthenticationManager authenticationManager;

  private final UserDetailsService userDetailsService;

  private final JwtUtil jwtUtil;

  public AuthController(
      AuthenticationManager authenticationManager,
      UserDetailsService userDetailsService,
      JwtUtil jwtUtil) {
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
    this.jwtUtil = jwtUtil;
  }

  @Override
  public ResponseEntity<?> authenticate(AuthenticationRequestData authenticationRequestData,
                                        HttpServletResponse response) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authenticationRequestData.getUsername(),
              authenticationRequestData.getPassword())
      );
    } catch (BadCredentialsException e) {
      return ResponseEntity
          .badRequest()
          .body(Error.builder()
              .code(400)
              .message("Incorrect username or password")
              .build());
    }

    UserDetails userDetails = userDetailsService
        .loadUserByUsername(authenticationRequestData.getUsername());
    final String jwt = jwtUtil.generateToken(userDetails);
//    Cookie cookie = new Cookie("tkn", jwt);
    // 7 days
//    cookie.setMaxAge(7 * 24 * 60 * 60);
//    cookie.setSecure(true);
//    cookie.setHttpOnly(true);
//    response.addCookie(cookie);

    return ResponseEntity.ok(new AuthenticationResponseData(jwt));
  }
}
