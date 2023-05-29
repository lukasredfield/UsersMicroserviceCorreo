package com.correoargentino.services.user.domain.primitive;

public abstract class Entity<T> {
  private final T id;

  protected Entity(T id) {
    this.id = id;
  }

  public T getId() {
    return id;
  }
}
