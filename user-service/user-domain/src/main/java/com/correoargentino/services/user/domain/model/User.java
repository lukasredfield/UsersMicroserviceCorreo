package com.correoargentino.services.user.domain.model;

import com.correoargentino.services.user.domain.primitive.AggregateRoot;
import java.util.UUID;

public class User extends AggregateRoot {
  public User(UUID id) {
    super(id);
  }
}
