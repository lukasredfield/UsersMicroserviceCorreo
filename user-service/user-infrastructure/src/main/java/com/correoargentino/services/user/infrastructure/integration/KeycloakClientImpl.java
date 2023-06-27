package com.correoargentino.services.user.infrastructure.integration;

import com.correoargentino.services.user.application.exception.UserAlreadyExistException;
import com.correoargentino.services.user.application.port.output.KeycloakClient;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakClientImpl implements KeycloakClient {
  private final UsersResource usersResource;

  @Override
  public UUID createUser(String firstName, String lastName,
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

    try (var response = usersResource.create(user)) {
      log.info("Register keycloak client status: {}", response.getStatus());

      if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {

        if (response.getStatus() == HttpStatus.CONFLICT.value()) {
          throw new UserAlreadyExistException(user.getUsername());
        }

        throw new BadRequestException();
      }

      return UUID.fromString(CreatedResponseUtil.getCreatedId(response));
    }
  }

  @Override
  public void deleteUser(UUID id) {
    var userResource = usersResource.get(id.toString());

    try {
      userResource.remove();
    } catch (WebApplicationException e) {
      throw new RuntimeException("Error al eliminar el usuario", e);
    }
  }

  @Override
  public void updateUser(UUID id, String firstName, String lastName, String emailAddress) {
    var userResource = usersResource.get(id.toString());

    var user = userResource.toRepresentation();
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setEmail(emailAddress);

    try {
      userResource.update(user);
    } catch (WebApplicationException e) {
      throw new RuntimeException("Error al actualizar el usuario", e);
    }
  }

  @Override
  public void verifyUser(UUID id) {
    var userResource = usersResource.get(id.toString());

    try {
      userResource.sendVerifyEmail();
    } catch (WebApplicationException e) {
      throw new RuntimeException("Error al actualizar el usuario", e);
    }
  }

  @Override
  public UUID activateUser(String token) {
    var userResource = usersResource.get(token);

    UserRepresentation user = userResource.toRepresentation();
    user.setEmailVerified(true);

    try {
      userResource.update(user);
      return UUID.fromString(user.getId());
    } catch (WebApplicationException e) {
      throw new RuntimeException("Error al actualizar el usuario", e);
    }
  }

  @Override
  public void changeUserPassword(UUID id, String password) {
    var userResource = usersResource.get(id.toString());

    var credential = new CredentialRepresentation();
    credential.setTemporary(false);
    credential.setType(CredentialRepresentation.PASSWORD);
    credential.setValue(password);

    try {
      userResource.resetPassword(credential);
    } catch (WebApplicationException e) {
      throw new RuntimeException("Error al actualizar el usuario", e);
    }
  }
}