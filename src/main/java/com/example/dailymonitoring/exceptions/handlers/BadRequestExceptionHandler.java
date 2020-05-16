package com.example.dailymonitoring.exceptions.handlers;

import com.example.dailymonitoring.exceptions.BadRequestException;
import com.example.dailymonitoring.models.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Sirghi Mihail
 */
@ControllerAdvice
public class BadRequestExceptionHandler {

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity resourceNotFoundError(BadRequestException exception) {
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
