package com.example.dailyMonitoring.controllers;

import com.example.dailyMonitoring.controllers.api.UserApi;
import com.example.dailyMonitoring.models.Error;
import com.example.dailyMonitoring.models.UserData;
import com.example.dailyMonitoring.services.UserService;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
public class UserController implements UserApi {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Override
  public ResponseEntity<?> userCreate(@Valid UserData userData) {
    UserData result = this.userService.createUser(userData);
    return result.getId() != null
            ? ResponseEntity.status(HttpStatus.CREATED).body(result)
            : ResponseEntity.badRequest()
            .body(Error
                    .builder()
                    .code(400)
                    .message("Failed to created user.")
                    .description("Username or password already exists.")
                    .build());
  }

  @Override
  public ResponseEntity<?> userGet(@Min(1) Long userId) {
    UserData result = this.userService.getUserById(userId);
    return result.getId() != null
            ? ResponseEntity.ok().body(result)
            : ResponseEntity.notFound().build();
  }

  @Override
  public ResponseEntity<?> userDelete(@Min(1) Long userId) {
    return this.userService.deleteUser(userId)
            ? ResponseEntity.status(204).build()
            : ResponseEntity.notFound().build();
  }



  /*
    TODO:
    1. method PUT - update existing user (read about status codes)
    2. method DELETE - userId as param, set status to inactive *Done*
    3. Unit tests  *added two tests*

    FIXME:
    1. on user creation same email bug *Done*

    H2 - database
    http://localhost:8080/h2-console
   */
}
