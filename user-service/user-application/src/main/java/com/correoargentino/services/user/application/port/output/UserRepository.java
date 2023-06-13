package com.correoargentino.services.user.application.port.output;

import com.correoargentino.services.user.application.exception.UserNotFoundException;
import com.correoargentino.services.user.domain.model.User;
import com.correoargentino.services.user.domain.model.UserKeycloak;


import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
  Optional<User> find(UUID id) throws UserNotFoundException;
  void save(User user);

  void safe(UserKeycloak userKeycloak);

  void delete(UUID id);
}
