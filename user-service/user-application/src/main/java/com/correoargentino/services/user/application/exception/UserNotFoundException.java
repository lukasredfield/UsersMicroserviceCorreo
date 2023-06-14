package com.correoargentino.services.user.application.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(UUID userId) {
    super("User with id " + userId.toString() + " was not found!");
  }
}
