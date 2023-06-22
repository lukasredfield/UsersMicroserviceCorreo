package com.correoargentino.services.user.infrastructure.persistence.entity;

import com.correoargentino.services.user.domain.model.Preferences;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

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

    @JdbcTypeCode(SqlTypes.JSON)
    private Preferences preferences;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = true)
    private LocalDateTime deletedAt;
}
