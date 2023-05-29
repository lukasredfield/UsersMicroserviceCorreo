package com.correoargentino.services.user.application.port.input;

import com.correoargentino.services.user.application.command.CreateUserCommand;
import com.correoargentino.services.user.application.command.DeleteUserCommand;
import com.correoargentino.services.user.application.command.UpdateUserCommand;
import com.correoargentino.services.user.application.query.GetUserQuery;
import com.correoargentino.services.user.application.query.model.User;
import jakarta.validation.Valid;
import java.util.UUID;

public interface UserService {
  UUID createUser(@Valid CreateUserCommand command);

  void updateUser(@Valid UpdateUserCommand command);

  void deleteUser(@Valid DeleteUserCommand command);

  User getUser(@Valid GetUserQuery query);
}
