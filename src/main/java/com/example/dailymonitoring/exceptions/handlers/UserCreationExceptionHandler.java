package com.example.dailymonitoring.exceptions.handlers;

import com.example.dailymonitoring.exceptions.UserCreationException;
import com.example.dailymonitoring.models.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Sirghi Mihail
 */
@ControllerAdvice
public class UserCreationExceptionHandler {

  @ExceptionHandler(UserCreationException.class)
  public ResponseEntity<Error> userCreationException(UserCreationException exception) {
    return ResponseEntity
        .status(400)
        .body(Error
            .builder()
            .code(400)
            .message(exception.getMessage())
            .build()
        );
  }
}
