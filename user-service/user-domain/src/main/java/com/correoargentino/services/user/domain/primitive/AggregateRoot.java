package com.correoargentino.services.user.domain.primitive;

import java.util.UUID;

public abstract class AggregateRoot extends Entity<UUID> {
  protected AggregateRoot(UUID id) {
    super(id);
  }
}
