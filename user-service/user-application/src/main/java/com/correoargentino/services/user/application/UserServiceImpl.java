package com.correoargentino.services.user.application;

import com.correoargentino.services.user.application.command.CreateUserCommand;
import com.correoargentino.services.user.application.command.DeleteUserCommand;
import com.correoargentino.services.user.application.command.UpdateUserCommand;
import com.correoargentino.services.user.application.messaging.MessageBus;
import com.correoargentino.services.user.application.port.input.UserService;
import com.correoargentino.services.user.application.port.output.KeycloakClient;
import com.correoargentino.services.user.application.query.GetUserQuery;
import com.correoargentino.services.user.application.query.model.User;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final MessageBus messageBus;
  private final KeycloakClient keycloakClient;

  @Override
  public UUID createUser(String firstName, String lastName,
                         String emailAddress, String phoneNumber, String password) {

    var id =
        UUID.randomUUID(); // keycloakClient.register(firstName, lastName, emailAddress, password);

    messageBus.dispatch(new CreateUserCommand(id, firstName, lastName, emailAddress, phoneNumber));

    return id;
  }

  @Override
  public void updateUser(UUID id, String firstName,
                         String lastName, String emailAddress, String phoneNumber) {
    messageBus.dispatch(new UpdateUserCommand(id, firstName, lastName, emailAddress, phoneNumber));
  }


  @Override
  public void deleteUser(UUID id) {
    messageBus.dispatch(new DeleteUserCommand(id));
  }

  @Override
  public User getUser(UUID id) {
    return messageBus.request(new GetUserQuery(id));
  }

}
