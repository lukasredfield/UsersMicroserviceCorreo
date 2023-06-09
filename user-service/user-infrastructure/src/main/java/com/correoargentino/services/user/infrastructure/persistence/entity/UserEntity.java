package com.correoargentino.services.user.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Entity
@Table(name = "users")
public class UserEntity {
  @Id
  @Value("${keycloak.client-id}")
  private UUID clientId;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "email_address")
  private String emailAddress;

  @Column(name = "phone_number", nullable = true)
  private String phoneNumber;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
