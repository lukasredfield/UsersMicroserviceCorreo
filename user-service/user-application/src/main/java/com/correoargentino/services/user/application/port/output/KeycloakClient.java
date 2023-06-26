package com.correoargentino.services.user.application.port.output;

import java.util.UUID;

public interface KeycloakClient {
  UUID createUser(String firstName, String lastName, String emailAddress, String password);

  void deleteUser(UUID id);

  void updateUser(UUID id, String firstName, String lastName, String emailAddress);

  void verifyUser(UUID id);

  UUID activateUser(String token);

  void changeUserPassword(UUID id, String password);
}

