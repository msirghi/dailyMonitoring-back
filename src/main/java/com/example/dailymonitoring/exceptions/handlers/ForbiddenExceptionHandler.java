package com.example.dailymonitoring.exceptions.handlers;

import com.example.dailymonitoring.exceptions.ForbiddenException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ForbiddenExceptionHandler {

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity forbiddenHandler() {
    return ResponseEntity.status(402).build();
  }
}
