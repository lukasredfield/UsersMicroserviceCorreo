package com.correoargentino.services.user.application.port.output;

import com.correoargentino.services.user.application.exception.UserNotFoundException;
import com.correoargentino.services.user.domain.model.User;


import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
  Optional<User> find(UUID id) throws UserNotFoundException;
  void save(User user);
  void delete(UUID id);
}
