package com.correoargentino.services.user.infrastructure.integration;

import com.correoargentino.services.user.application.port.output.KeycloakClient;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;


import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakClientImpl implements KeycloakClient {
  private final UsersResource usersResource;

  private UUID createdUserId;
  public UUID register(String firstName, String lastName,
                       String emailAddress, String password) {

    var credential = new CredentialRepresentation();
    credential.setTemporary(false);
    credential.setType(CredentialRepresentation.PASSWORD);
    credential.setValue(password);

    var user = new UserRepresentation();
    user.setUsername(emailAddress);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setEmail(emailAddress);
    user.setCredentials(List.of(credential));
    user.setEnabled(true);


    //CREAR EXCEPCIÓN PERSONALIZADA

    try (var response = usersResource.create(user)) {
      if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
        createdUserId = UUID.fromString(CreatedResponseUtil.getCreatedId(response));
        return createdUserId;
      }

    } catch (Exception e) {
      log.error(e.getMessage());
    }

    return null;
  }

  public UUID getCreatedUserId() {
    return createdUserId;
  }

  @Override
  public void deleteUser(UUID id) {
    UserResource userResource = usersResource.get(id.toString());

    try {
      userResource.remove();
    } catch (WebApplicationException e) {
      throw new RuntimeException("Error al eliminar el usuario", e);
    }
  }

  @Override
  public void updateUser(UUID id, String firstName, String lastName, String emailAddress) {
    UserResource userResource = usersResource.get(id.toString());

    UserRepresentation user = userResource.toRepresentation();
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setEmail(emailAddress);

    try {
      userResource.update(user);
    } catch (WebApplicationException e) {
      throw new RuntimeException("Error al actualizar el usuario", e);
    }
  }

}