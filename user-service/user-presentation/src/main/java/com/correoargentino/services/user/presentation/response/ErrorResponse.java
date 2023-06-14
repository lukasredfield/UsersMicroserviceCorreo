package com.correoargentino.services.user.presentation.response;

import java.net.URI;
import java.time.LocalDateTime;

public record ErrorResponse(String title,
                            String detail,
                            int status,
                            LocalDateTime timestamp,
                            URI instance) {
}
