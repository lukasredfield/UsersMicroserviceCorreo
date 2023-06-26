package com.correoargentino.services.user.domain.model;

import com.correoargentino.services.user.domain.event.UserActivatedEvent;
import com.correoargentino.services.user.domain.event.UserCreatedEvent;
import com.correoargentino.services.user.domain.event.UserDeletedEvent;
import com.correoargentino.services.user.domain.event.UserPasswordResetEvent;
import com.correoargentino.services.user.domain.event.UserUpdatedEvent;
import com.correoargentino.services.user.domain.primitive.AggregateRoot;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends AggregateRoot<UUID> {
  private String username;
  private String firstName;
  private String lastName;
  private String emailAddress;
  private String phoneNumber;
  private Parameters parameters;
  private UserStatus status;

  public void create(UUID id, String firstName,
                     String lastName, String emailAddress, String phoneNumber) {
    apply(new UserCreatedEvent(id, emailAddress, firstName,
        lastName, emailAddress, phoneNumber, new Parameters(), LocalDateTime.now()));
  }

  public void update(String firstName, String lastName, String emailAddress, String phoneNumber) {
    apply(new UserUpdatedEvent(
        firstName, lastName, emailAddress, phoneNumber, LocalDateTime.now()));
  }

  public void delete() {
    apply(new UserDeletedEvent(LocalDateTime.now()));
  }

  public void activate() {
    apply(new UserActivatedEvent(LocalDateTime.now()));
  }

  public void resetPassword() {
    apply(new UserPasswordResetEvent(LocalDateTime.now()));
  }

  public void onEvent(UserCreatedEvent event) {
    this.id = event.id();
    this.username = event.username();
    this.firstName = event.firstName();
    this.lastName = event.lastName();
    this.emailAddress = event.emailAddress();
    this.phoneNumber = event.phoneNumber();
    this.parameters = event.parameters();
    this.status = UserStatus.INACTIVE;
    this.createdAt = event.createdAt();
    this.updatedAt = event.createdAt();
  }

  public void onEvent(UserUpdatedEvent event) {
    this.firstName = event.firstName();
    this.lastName = event.lastName();
    this.emailAddress = event.emailAddress();
    this.phoneNumber = event.phoneNumber();
    this.updatedAt = event.updatedAt();
  }

  public void onEvent(UserDeletedEvent event) {
    this.deletedAt = event.deletedAt();
  }

  public void onEvent(UserActivatedEvent event) {
    this.status = UserStatus.ACTIVE;
    this.updatedAt = event.activatedAt();
  }

  public void onEvent(UserPasswordResetEvent event) {
    this.updatedAt = event.resetAt();
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
    User user = (User) o;
    return Objects.equals(firstName, user.firstName) &&
        Objects.equals(lastName, user.lastName) &&
        Objects.equals(emailAddress, user.emailAddress) &&
        Objects.equals(phoneNumber, user.phoneNumber) &&
        Objects.equals(parameters, user.parameters) &&
        Objects.equals(status, user.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(),
        firstName, lastName, emailAddress, phoneNumber, parameters, status);
  }
}


