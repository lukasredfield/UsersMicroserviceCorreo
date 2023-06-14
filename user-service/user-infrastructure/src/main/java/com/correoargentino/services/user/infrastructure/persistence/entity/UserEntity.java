package com.correoargentino.services.user.infrastructure.persistence.entity;

import com.correoargentino.services.user.domain.model.Preferences;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {
  @Id
  private UUID id;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  private String emailAddress;

  @Column(nullable = true)
  private String phoneNumber;

  @JdbcTypeCode(SqlTypes.JSON)
  private Preferences preferences;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime updatedAt;
}
