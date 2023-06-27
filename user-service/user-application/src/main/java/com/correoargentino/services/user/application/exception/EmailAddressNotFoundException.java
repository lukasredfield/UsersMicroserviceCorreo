package com.correoargentino.services.user.application.exception;

public class EmailAddressNotFoundException extends RuntimeException {
  public EmailAddressNotFoundException(String emailAddress) {
    super("User with the email address " + emailAddress + " was not found!");
  }
}
