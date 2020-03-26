package com.example.dailyMonitoring.controllers;

import com.example.dailyMonitoring.controllers.api.UserApi;
import com.example.dailyMonitoring.models.EmailData;
import com.example.dailyMonitoring.models.Error;
import com.example.dailyMonitoring.models.UserData;
import com.example.dailyMonitoring.models.UsernameData;
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
                    .description("Username or email is already taken.")
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

  @Override
  public ResponseEntity<?> userUpdate(@Min(1) Long userId, @Valid UserData userData) {
    UserData result = this.userService.updateUser(userId, userData);

    if (result.getId() == -2L) {
      return ResponseEntity.badRequest()
              .body(Error
                      .builder()
                      .code(400)
                      .message("Failed to update user.")
                      .description("Username is already taken.")
                      .build());
    } else if (result.getId() == -1L) {
      return ResponseEntity.badRequest()
              .body(Error
                      .builder()
                      .code(400)
                      .message("Failed to update user.")
                      .description("Email is already taken.")
                      .build());
    }

    return result.getId() != null
            ? ResponseEntity.status(200).body(result)
            : ResponseEntity.notFound().build();
  }


  @Override
  public ResponseEntity<?> userUpdatePasswordOnly(@Min(1) Long userId , @Valid UserData userData) {
    return this.userService.updateUserPasswordOnly(userId ,userData)
            ? ResponseEntity.status(204).build()
            : ResponseEntity.notFound().build();
  }

  @Override
  public ResponseEntity<?> userUpdateEmailOnly(@Min(1) Long userId , @Valid EmailData emailData) {
    return this.userService.updateUserEmailOnly(userId ,emailData)
            ? ResponseEntity.status(204).build()
            : ResponseEntity.badRequest()
            .body(Error
                    .builder()
                    .code(400)
                    .message("Failed to update user.")
                    .description("Email is already taken.")
                    .build());
  }

  @Override
  public ResponseEntity<?> userUpdateUsernameOnly(@Min(1) Long userId , @Valid UsernameData usernameData) {
    return this.userService.updateUserUsernameOnly(userId , usernameData)
            ? ResponseEntity.status(204).build()
            : ResponseEntity.badRequest()
            .body(Error
                    .builder()
                    .code(400)
                    .message("Failed to update user.")
                    .description("Username is already taken.")
                    .build());
  }

  // TODO: 25.03.2020
  /*
    Endpoints:
    - update email only (*done*)
    - update username only (*done*)
    - password (*done*)
    - pattern annotation (*done*)
    - add Data class only for email and username (*done*)
    - tests
   */


  /*
    H2 - database
    http://localhost:8080/h2-console
   */
}
