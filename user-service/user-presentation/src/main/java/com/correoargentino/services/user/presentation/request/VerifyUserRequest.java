package com.correoargentino.services.user.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record VerifyUserRequest(@NotBlank String username) {
}
