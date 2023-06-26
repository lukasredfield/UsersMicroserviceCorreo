package com.correoargentino.services.user.application.port.output;

import com.correoargentino.services.user.application.query.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserQuery {
  Optional<User> getById(UUID id);

  Optional<User> getByUsername(String username);

  Optional<User> getByEmailAddress(String emailAddress);

  List<User> findUsers();
}
