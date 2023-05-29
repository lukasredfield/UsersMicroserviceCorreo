package com.correoargentino.services.user.application.messaging.exception;

public class HandlerNotFoundException extends RuntimeException {
  public HandlerNotFoundException(String message) {
    super(message);
  }
}
