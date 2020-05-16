package com.example.dailymonitoring.exceptions.handlers;

import com.example.dailymonitoring.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Sirghi Mihail
 */
@ControllerAdvice
public class ResourceNotFoundExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity resourceNotFoundError() {
    return ResponseEntity.status(404).build();
  }
}
