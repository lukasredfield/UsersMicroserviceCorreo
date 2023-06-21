package com.correoargentino.services.user.presentation.exception;

import com.correoargentino.services.user.application.exception.AccessTokenExpiredException;
import com.correoargentino.services.user.application.exception.UserAlreadyExistException;
import com.correoargentino.services.user.application.exception.UserNotFoundException;
import com.correoargentino.services.user.presentation.response.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleException(UserNotFoundException exception) {
    log.error(exception.getMessage(), exception);

    return new ResponseEntity<>(
        new ErrorResponse("User Not Found", exception.getMessage(),
            HttpStatus.NOT_FOUND.value(), LocalDateTime.now(),
            URI.create(ServletUriComponentsBuilder.
                fromCurrentRequestUri().toUriString())), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(AccessTokenExpiredException.class)
  public ResponseEntity<ErrorResponse> handleException(AccessTokenExpiredException exception) {
    log.error(exception.getMessage(), exception);

    return new ResponseEntity<>(
        new ErrorResponse("Access Token Expired", exception.getMessage(),
            HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),
            URI.create(ServletUriComponentsBuilder.
                fromCurrentRequestUri().toUriString())), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UserAlreadyExistException.class)
  public ResponseEntity<ErrorResponse> handleException(UserAlreadyExistException exception) {
    log.error(exception.getMessage(), exception);

    return new ResponseEntity<>(
        new ErrorResponse("User Already Exist", exception.getMessage(),
            HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),
            URI.create(ServletUriComponentsBuilder.
                fromCurrentRequestUri().toUriString())), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleException(ConstraintViolationException exception) {
    log.error(exception.getMessage(), exception);

    var violations = new LinkedHashMap<String, Object>();

    for (var violation : exception.getConstraintViolations()) {
      violations.put(violation.getPropertyPath().toString(), violation.getMessage());
    }

    return new ResponseEntity<>(
        new ErrorResponse("Constraint Violation", "Parameters didn't validate.",
            violations, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),
            URI.create(ServletUriComponentsBuilder.
                fromCurrentRequestUri().toUriString())), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException exception) {
    log.error(exception.getMessage(), exception);

    var violations = new LinkedHashMap<String, Object>();

    for (var fieldError : exception.getBindingResult().getFieldErrors()) {
      violations.put(fieldError.getField(), fieldError.getDefaultMessage());
    }

    return new ResponseEntity<>(
        new ErrorResponse("Argument Not Valid", "Request parameters didn't validate.",
            violations, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),
            URI.create(ServletUriComponentsBuilder.
                fromCurrentRequestUri().toUriString())), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponse> handleException(RuntimeException exception) {
    log.error(exception.getMessage(), exception);

    return new ResponseEntity<>(
        new ErrorResponse("internal Server Error", exception.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now(),
            URI.create(ServletUriComponentsBuilder.
                fromCurrentRequestUri().toUriString())), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
