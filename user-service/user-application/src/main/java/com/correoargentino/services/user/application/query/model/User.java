package com.correoargentino.services.user.application.query.model;

import com.correoargentino.services.user.domain.model.Preferences;
import java.time.LocalDateTime;
import java.util.UUID;


public record User(UUID id,
                   String firstName,
                   String lastName,
                   String emailAddress,
                   String phoneNumber,
                   Preferences preferences,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt) {
}
