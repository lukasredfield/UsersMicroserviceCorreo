package com.correoargentino.services.user.infrastructure.persistence.entity;

import com.correoargentino.services.user.domain.model.Parameters;
import com.correoargentino.services.user.domain.model.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Entity
@Table(name = "users",
    indexes = {@Index(name = "users_username_unique_index", columnList = "username", unique = true),
        @Index(name = "users_created_at_index", columnList = "deletedAt")})
public class UserEntity {
  @Id
  private UUID id;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  private String emailAddress;

  @Column(nullable = true)
  private String phoneNumber;

  @Column(nullable = false)
  @ColumnDefault("'{}'::jsonb")
  @JdbcTypeCode(SqlTypes.JSON)
  private Parameters parameters;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserStatus status;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @Column(nullable = true)
  private LocalDateTime deletedAt;
}
