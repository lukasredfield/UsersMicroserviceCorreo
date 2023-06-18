package com.correoargentino.services.user.domain.model;

import com.correoargentino.services.user.domain.primitive.AggregateRoot;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class User extends AggregateRoot<UUID> {
  private String firstName;
  private String lastName;
  private String emailAddress;
  private String phoneNumber;
  private Preferences preferences;

  public static User create(UUID id, String firstName,
                            String lastName, String emailAddress, String phoneNumber) {
    var user = new User();
    user.id = id;
    user.firstName = firstName;
    user.lastName = lastName;
    user.emailAddress = emailAddress;
    user.phoneNumber = phoneNumber;
    user.createdAt = LocalDateTime.now();
    user.updatedAt = user.createdAt;
    return user;
  }

  public void update(String firstName, String lastName, String emailAddress, String phoneNumber) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
    this.phoneNumber = phoneNumber;

    if (this.getCreatedAt() == null) {
      this.setCreatedAt(LocalDateTime.now());
    }

    this.setUpdatedAt(LocalDateTime.now());
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
        Objects.equals(preferences, user.preferences);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), firstName, lastName, emailAddress, phoneNumber,
        preferences);
  }
}


