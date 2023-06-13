package com.correoargentino.services.user.presentation.request;

import java.time.LocalDateTime;

public record CreateUserRequest(

        String userName,
        String firstName,
        String lastName,
        String emailAddress,
        String password) {
}

