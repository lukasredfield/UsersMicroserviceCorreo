package com.correoargentino.services.user.domain.primitive;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class AggregateRoot<T> extends Entity<T> {
  private final List<Event> events = new ArrayList<>();
  protected LocalDateTime createdAt;
  protected LocalDateTime updatedAt;

  public Collection<Event> getUncommittedEvents() {
    return Collections.unmodifiableList(events);
  }

  protected void apply(Event event) {
    events.add(Objects.requireNonNull(event));
    invokeEventHandler(event);
  }

  public void loadFromHistory(List<Event> events) {
    events.forEach(this::invokeEventHandler);
  }

  public void clearEvent() {
    events.clear();
  }

  private <E extends Event> void invokeEventHandler(E event) {
    try {
      var method = this.getClass().getDeclaredMethod("onEvent", event.getClass());
      method.invoke(this, event);
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      throw new UnsupportedOperationException(
          String.format("Aggregate '%s' doesn't handle event type '%s'",
              this.getClass(), event.getClass()), e);
    }
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    AggregateRoot<?> that = (AggregateRoot<?>) o;
    return Objects.equals(createdAt, that.createdAt) &&
        Objects.equals(updatedAt, that.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), createdAt, updatedAt);
  }
}
