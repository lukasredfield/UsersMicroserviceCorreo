package com.correoargentino.services.user.application.port.output;

import java.util.UUID;

/**
 * Interfaz que define las operaciones de cliente para interactuar con Keycloak.
 */
public interface KeycloakClient {
  UUID register(String firstName, String lastName, String emailAddress, String password);
  void deleteUser(UUID id);
  void updateUser(UUID id, String firstName, String lastName, String emailAddress);
  UUID getCreatedUserId();
}
