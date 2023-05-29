package com.correoargentino.services.user.application;

import com.correoargentino.services.user.application.command.CreateUserCommand;
import com.correoargentino.services.user.application.command.DeleteUserCommand;
import com.correoargentino.services.user.application.command.UpdateUserCommand;
import com.correoargentino.services.user.application.port.input.UserService;
import com.correoargentino.services.user.application.messaging.MessageBus;
import com.correoargentino.services.user.application.query.GetUserQuery;
import com.correoargentino.services.user.application.query.model.User;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final MessageBus messageBus;

  @Override
  public UUID createUser(CreateUserCommand command) {
    return messageBus.dispatch(command);
  }

  @Override
  public void updateUser(UpdateUserCommand command) {
    messageBus.dispatch(command);
  }

  @Override
  public void deleteUser(DeleteUserCommand command) {
    messageBus.dispatch(command);
  }

  @Override
  public User getUser(GetUserQuery query) {
    return messageBus.dispatch(query);
  }
}
