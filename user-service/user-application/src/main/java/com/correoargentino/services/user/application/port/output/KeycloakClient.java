package com.correoargentino.services.user.application.port.output;

import java.util.UUID;

public interface KeycloakClient {
  public UUID register(String firstName, String lastName, String emailAddress, String password);
}
