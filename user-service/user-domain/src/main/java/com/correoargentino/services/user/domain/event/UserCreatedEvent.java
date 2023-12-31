package com.correoargentino.services.user.domain.event;

import com.correoargentino.services.user.domain.model.Parameters;
import com.correoargentino.services.user.domain.primitive.Event;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserCreatedEvent(UUID id,
                               String username,
                               String firstName,
                               String lastName,
                               String emailAddress,
                               String phoneNumber,
                               Parameters parameters,
                               LocalDateTime createdAt) implements Event {
}
