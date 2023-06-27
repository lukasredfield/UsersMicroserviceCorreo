package com.correoargentino.services.user.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record RecoveryUserPasswordRequest(@NotBlank String username) {
}
