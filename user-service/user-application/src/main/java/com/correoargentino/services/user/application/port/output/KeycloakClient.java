package com.correoargentino.services.user.application.port.output;

import java.util.UUID;

public interface KeycloakClient {
  public UUID register(String firstName, String lastName, String emailAddress, String password);
  void deleteUser(UUID id);
  void updateUser(UUID id, String firstName, String lastName, String emailAddress);

}
