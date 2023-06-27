package com.correoargentino.services.user.application;

import com.correoargentino.services.user.application.command.ActivateUserCommand;
import com.correoargentino.services.user.application.command.CreateUserCommand;
import com.correoargentino.services.user.application.command.DeleteUserCommand;
import com.correoargentino.services.user.application.command.ResetUserPasswordCommand;
import com.correoargentino.services.user.application.command.UpdateUserCommand;
import com.correoargentino.services.user.application.messaging.MessageBus;
import com.correoargentino.services.user.application.port.input.UserService;
import com.correoargentino.services.user.application.port.output.KeycloakClient;
import com.correoargentino.services.user.application.query.FindUsersQuery;
import com.correoargentino.services.user.application.query.GetUserByUsernameQuery;
import com.correoargentino.services.user.application.query.GetUserQuery;
import com.correoargentino.services.user.application.query.model.User;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final MessageBus messageBus;
  private final KeycloakClient keycloakClient;

  @Override
  public List<User> searchUsers(String query) {
    return messageBus.request(new FindUsersQuery());
  }

  @Override
  public UUID createUser(String firstName, String lastName,
                         String emailAddress, String phoneNumber, String password) {
    var id = keycloakClient.createUser(firstName, lastName, emailAddress, password);
    messageBus.dispatch(new CreateUserCommand(id, firstName, lastName, emailAddress, phoneNumber));
    return id;
  }

  @Override
  public void updateUser(UUID id, String firstName,
                         String lastName, String emailAddress, String phoneNumber) {
    keycloakClient.updateUser(id, firstName, lastName, emailAddress);
    messageBus.dispatch(new UpdateUserCommand(id, firstName, lastName, emailAddress, phoneNumber));
  }

  @Override
  public void deleteUser(UUID id) {
    keycloakClient.deleteUser(id);
    messageBus.dispatch(new DeleteUserCommand(id));
  }

  @Override
  public void verifyUser(String username) {
    var user = messageBus.request(new GetUserByUsernameQuery(username));
    keycloakClient.verifyUser(user.id());
  }

  @Override
  public void activateUser(String token) {
    var id = keycloakClient.activateUser(token);
    messageBus.dispatch(new ActivateUserCommand(id));
  }

  @Override
  public void changeUserPassword(UUID id, String password) {
    keycloakClient.changeUserPassword(id, password);
    messageBus.dispatch(new ResetUserPasswordCommand(id));
  }

  @Override
  public User getUser(UUID id) {
    return messageBus.request(new GetUserQuery(id));
  }
}