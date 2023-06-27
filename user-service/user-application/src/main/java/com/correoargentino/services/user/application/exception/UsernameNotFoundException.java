package com.correoargentino.services.user.application.exception;

public class UsernameNotFoundException extends RuntimeException {
  public UsernameNotFoundException(String username) {
    super("User " + username + " was not found!");
  }
}