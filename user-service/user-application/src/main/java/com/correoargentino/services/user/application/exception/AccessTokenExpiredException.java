package com.correoargentino.services.user.application.exception;

public class AccessTokenExpiredException extends RuntimeException {
  public AccessTokenExpiredException() {
    super("Access token is expired");
  }
}
