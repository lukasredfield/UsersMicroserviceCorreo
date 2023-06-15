package com.correoargentino.services.user.application.port.output;

import com.correoargentino.services.user.application.query.model.User;
import java.util.Optional;
import java.util.UUID;

public interface UserReadRepository {
  Optional<User> findById(UUID id);
}
