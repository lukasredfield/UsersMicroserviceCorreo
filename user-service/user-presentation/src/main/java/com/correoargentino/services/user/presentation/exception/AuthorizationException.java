package com.correoargentino.services.user.presentation.exception;

public class AuthorizationException extends RuntimeException {
  public AuthorizationException() {
    super("Access token invalid!");
  }
}
