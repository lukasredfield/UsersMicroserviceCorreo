package com.correoargentino.services.user.application.query.model;

import java.time.LocalDateTime;


public record User(int id,  String firstName,
         String lastName,
         String emailAddress,
         String phoneNumber,
         LocalDateTime createdAt,
         LocalDateTime updatedAt) {
}
