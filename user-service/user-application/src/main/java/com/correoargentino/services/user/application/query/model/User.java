package com.correoargentino.services.user.application.query.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public record User(UUID id,
                   String username,
                   String firstName,
                   String lastName,
                   String emailAddress,
                   String phoneNumber,
                   Map<String, Object> parameters,
                   String status,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt) {
}
