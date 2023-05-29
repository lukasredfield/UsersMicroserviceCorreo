package com.correoargentino.services.user.presentation.exception;

import com.correoargentino.services.user.application.exception.UserNotFoundException;
import com.correoargentino.services.user.presentation.response.ErrorResponse;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleException(UserNotFoundException exception) {
    log.error(exception.getMessage(), exception);

    return new ResponseEntity<>(
        new ErrorResponse("USER_NOT_FOUND",
            exception.getMessage(), LocalDateTime.now()), HttpStatus.NOT_FOUND);
  }
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponse> handleException(RuntimeException exception) {
    log.error(exception.getMessage(), exception);

    return new ResponseEntity<>(
        new ErrorResponse("INTERNAL_SERVER_ERROR",
            exception.getMessage(), LocalDateTime.now()), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
