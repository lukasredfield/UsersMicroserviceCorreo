package com.correoargentino.services.user.presentation.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
    @Size(max = 30)
    String firstName,
    @Size(max = 30)
    String lastName,
    @Email @Size(max = 255)
    String emailAddress,
    @Size(max = 20)
    String phoneNumber) {
}
