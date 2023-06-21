package com.correoargentino.services.user.application.exception;

public class UserAlreadyExistException extends RuntimeException {
  public UserAlreadyExistException(String username) {
    super("User with the username " + username + "already exist!");
  }
}
