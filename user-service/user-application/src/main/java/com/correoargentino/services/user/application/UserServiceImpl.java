package com.correoargentino.services.user.application;

import com.correoargentino.services.user.application.command.CreateUserCommand;
import com.correoargentino.services.user.application.command.DeleteUserCommand;
import com.correoargentino.services.user.application.command.UpdateUserCommand;
import com.correoargentino.services.user.application.messaging.MessageBus;
import com.correoargentino.services.user.application.port.input.UserService;
import com.correoargentino.services.user.application.port.output.KeycloakClient;
import com.correoargentino.services.user.application.query.GetUserQuery;
import com.correoargentino.services.user.application.query.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Clase: UserServiceImpl
 * <p>
 * Esta clase implementa la interfaz UserService y proporciona una implementación de los métodos.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final MessageBus messageBus;
  private final KeycloakClient keycloakClient;

  /**
   * Método: createUser
   * <p>
   * Crea un nuevo usuario con los datos proporcionados.
   */
  @Override
  public UUID createUser(String firstName, String lastName,
                         String emailAddress, String phoneNumber, String password) {

    keycloakClient.register(firstName, lastName, emailAddress, password);
    UUID id = keycloakClient.getCreatedUserId();

    messageBus.dispatch(new CreateUserCommand(id, firstName, lastName, emailAddress, phoneNumber));

    return id;
  }

  /**
   * Método: updateUser
   * <p>
   * Actualiza los datos de un usuario existente.
   */
  @Override
  public void updateUser(UUID id, String firstName,
                         String lastName, String emailAddress, String phoneNumber) {
    messageBus.dispatch(new UpdateUserCommand(id, firstName, lastName, emailAddress, phoneNumber));
  }

  /**
   * Método: deleteUser
   * <p>
   * Elimina un usuario existente.
   */

  @Override
  public void deleteUser(UUID id) {
    messageBus.dispatch(new DeleteUserCommand(id));
  }

  /**
   * Método: getUser
   * <p>
   * Obtiene los datos de un usuario existente.
   */
  @Override
  public User getUser(UUID id) {
    return messageBus.request(new GetUserQuery(id));
  }

}
