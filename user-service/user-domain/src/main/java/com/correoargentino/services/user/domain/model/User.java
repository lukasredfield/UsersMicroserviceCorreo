package com.correoargentino.services.user.domain.model;

import com.correoargentino.services.user.domain.primitive.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class User extends AggregateRoot<UUID> {
  private String firstName;
  private String lastName;
  private String emailAddress;
  private String phoneNumber;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

}


