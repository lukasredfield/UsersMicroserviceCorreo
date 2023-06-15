package com.correoargentino.services.user.presentation.request;

public record CreateUserRequest(
        String firstName,
        String lastName,
        String emailAddress,
        String phoneNumber,
        String password) {
}

