package com.correoargentino.services.user.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

public record CreateUserResponse(
    @Schema(description = "Unique identifier of the user.",
        example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    UUID id
) { }
