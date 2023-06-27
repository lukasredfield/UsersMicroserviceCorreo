package com.correoargentino.services.user.infrastructure.integration;

import com.correoargentino.services.user.application.port.output.KeycloakClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

/**
 * Implementación de la interfaz KeycloakClient que proporciona operaciones de cliente para interactuar con Keycloak.
 * <p>
 * Utiliza la anotación @RequiredArgsConstructor de Lombok para generar un constructor con argumentos para los campos marcados con 'final'.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakClientImpl implements KeycloakClient {
  private final UsersResource usersResource;

  private UUID createdUserId;

  /**
   * Método que registra un nuevo usuario en Keycloak.
   * <p>
   * Este método toma como parámetros el nombre, apellido, dirección de correo electrónico y contraseña del nuevo usuario.
   * Crea una representación de credencial con la contraseña proporcionada y la configura como no temporal.
   * Luego, crea una representación de usuario y configura los campos de nombre, apellido, dirección de correo electrónico, credenciales y habilitación.
   * Intenta crear el usuario llamando al método create() en usersResource y verifica el estado de respuesta.
   * Si el estado de respuesta es exitoso, se obtiene el ID del usuario creado y se devuelve como un objeto UUID.
   * Si se produce una excepción durante la creación del usuario, se registra un mensaje de error y se devuelve null.
   */
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

  /**
   * Método que actualiza un usuario en Keycloak.
   * <p>
   * Este método toma como parámetros el ID del usuario, el nombre, apellido y dirección de correo electrónico actualizados.
   * Utiliza el ID del usuario para obtener una instancia de UserResource de Keycloak.
   * A continuación, se obtiene la representación del usuario y se actualizan los campos de nombre, apellido y dirección de correo electrónico.
   * Finalmente, se llama al método update() en UserResource para actualizar el usuario en Keycloak.
   * Si se produce una excepción WebApplicationException durante la actualización, se lanza una RuntimeException con un mensaje de error.
   */
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