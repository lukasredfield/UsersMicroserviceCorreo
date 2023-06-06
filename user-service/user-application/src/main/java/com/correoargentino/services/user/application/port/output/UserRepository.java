package com.correoargentino.services.user.application.port.output;

import com.correoargentino.services.user.application.command.UpdateUserCommand;
import com.correoargentino.services.user.application.exception.UserNotFoundException;
import com.correoargentino.services.user.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
  Optional<User> find(UUID id) throws UserNotFoundException;

  void create(User user);

  void updateUser(UpdateUserCommand command);

  void delete(UUID id);
}
