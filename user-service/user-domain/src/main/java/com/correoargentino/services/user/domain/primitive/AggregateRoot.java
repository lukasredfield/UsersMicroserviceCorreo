package com.correoargentino.services.user.domain.primitive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AggregateRoot<T> extends Entity<T> {
  protected LocalDateTime createdAt;
  protected LocalDateTime updatedAt;

  protected AggregateRoot(T id, LocalDateTime createdAt, LocalDateTime updatedAt) {
    super(id);
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
}
