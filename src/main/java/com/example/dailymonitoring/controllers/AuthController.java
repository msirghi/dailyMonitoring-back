package com.example.dailymonitoring.controllers;

import com.example.dailymonitoring.controllers.api.AuthApi;
import com.example.dailymonitoring.models.auth.AuthenticationRequestData;
import com.example.dailymonitoring.models.auth.AuthenticationResponseData;
import com.example.dailymonitoring.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController implements AuthApi {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @Override
  public ResponseEntity<AuthenticationResponseData> authenticate(AuthenticationRequestData authenticationRequestData,
                                                                 HttpServletResponse response) {
    AuthenticationResponseData result = authService.authenticate(authenticationRequestData);

    Cookie cookie = new Cookie("jit", result.getRefreshToken());
    cookie.setMaxAge(7 * 24 * 60 * 60);
    cookie.setSecure(true);
    cookie.setHttpOnly(true);
    response.addCookie(cookie);

    return ResponseEntity.ok(result);
  }

  @Override
  public ResponseEntity<AuthenticationResponseData> renewToken(AuthenticationResponseData authData,
                                                               HttpServletResponse response) {
    return ResponseEntity.ok(authService.renewToken(authData));
  }
}
