package com.correoargentino.services.user.domain.primitive;

import java.util.Objects;

public abstract class Entity<T> {
  protected T id;

  public void setId(T id) {
    this.id = id;
  }
  public T getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Entity<?> entity = (Entity<?>) o;
    return Objects.equals(id, entity.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
