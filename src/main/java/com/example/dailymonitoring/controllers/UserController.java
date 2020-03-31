package com.example.dailymonitoring.controllers;

import com.example.dailymonitoring.controllers.api.UserApi;
import com.example.dailymonitoring.models.EmailData;
import com.example.dailymonitoring.models.Error;
import com.example.dailymonitoring.models.PasswordData;
import com.example.dailymonitoring.models.UserData;
import com.example.dailymonitoring.models.UsernameData;
import com.example.dailymonitoring.services.UserService;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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

    if (result.getId() != null) {
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
    }

    return result.getId() != null
        ? ResponseEntity.ok().body(result)
        : ResponseEntity.notFound().build();
  }


  @Override
  public ResponseEntity<?> userUpdatePasswordOnly(@Min(1) Long userId,
      @Valid PasswordData passwordData) {
    return this.userService.updateUserPasswordOnly(userId, passwordData)
        ? ResponseEntity.ok().build()
        : ResponseEntity.notFound().build();


  }

  @Override
  public ResponseEntity<?> userUpdateEmailOnly(@Min(1) Long userId, @Valid EmailData emailData) {
    return this.userService.updateUserEmailOnly(userId, emailData)
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest()
            .body(Error
                .builder()
                .code(400)
                .message("Failed to update user.")
                .description("Email is already taken.")
                .build());
  }

  @Override
  public ResponseEntity<?> userUpdateUsernameOnly(@Min(1) Long userId,
      @Valid UsernameData usernameData) {
    return this.userService.updateUserUsernameOnly(userId, usernameData)
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest()
            .body(Error
                .builder()
                .code(400)
                .message("Failed to update user.")
                .description("Username is already taken.")
                .build());
  }
}
