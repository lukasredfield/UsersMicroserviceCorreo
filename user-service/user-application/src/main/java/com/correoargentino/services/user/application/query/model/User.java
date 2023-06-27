package com.correoargentino.services.user.application.query.model;

import com.fasterxml.jackson.databind.JsonNode;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Registro (record) que representa un usuario.
 * Este registro es inmutable, es decir, una vez creado no puede modificarse.
*/
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
