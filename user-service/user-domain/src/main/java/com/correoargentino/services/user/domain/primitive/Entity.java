package com.correoargentino.services.user.domain.primitive;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class Entity<T> {
  private T id;

  protected Entity(T id) {
    this.id = id;
  }
}
