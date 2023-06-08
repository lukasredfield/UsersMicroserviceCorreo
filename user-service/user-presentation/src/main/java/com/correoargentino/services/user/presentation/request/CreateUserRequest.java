package com.correoargentino.services.user.presentation.request;

import java.time.LocalDateTime;

public record CreateUserRequest(
        String firstName,
        String lastName,
        String emailAddress,
        String phoneNumber) {
}

