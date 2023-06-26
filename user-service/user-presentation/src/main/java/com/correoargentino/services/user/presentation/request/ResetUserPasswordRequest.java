package com.correoargentino.services.user.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record ResetUserPasswordRequest(@NotBlank String password) {
}
