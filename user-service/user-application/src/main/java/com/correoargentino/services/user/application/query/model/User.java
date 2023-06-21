package com.correoargentino.services.user.application.query.model;

import com.fasterxml.jackson.databind.JsonNode;
import java.time.LocalDateTime;
import java.util.UUID;

public record User(UUID id,
                   String username,
                   String firstName,
                   String lastName,
                   String emailAddress,
                   String phoneNumber,
                   JsonNode preferences,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt) {
}
