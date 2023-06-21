package com.correoargentino.services.user.presentation.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
    @NotBlank @Size(max = 30)
    String firstName,
    @NotBlank @Size(max = 30)
    String lastName,
    @NotBlank @Email @Size(max = 255)
    String emailAddress,
    @Size(max = 20)
    String phoneNumber,
    @NotBlank
    String password) {
}

