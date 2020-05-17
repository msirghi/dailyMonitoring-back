package com.example.dailymonitoring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Sirghi Mihail
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserCreationException extends RuntimeException {

  public UserCreationException() {

  }

  public UserCreationException(String message) {
    super(message);
  }
}
