package com.correoargentino.services.user.presentation.request;

public record CreateUserRequest(
    String email,
    String name) {
}
