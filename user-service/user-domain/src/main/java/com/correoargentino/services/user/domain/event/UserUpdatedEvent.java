package com.correoargentino.services.user.domain.event;

import com.correoargentino.services.user.domain.primitive.Event;
import java.time.LocalDateTime;

public record UserUpdatedEvent(String firstName,
                               String lastName,
                               String emailAddress,
                               String phoneNumber,
                               LocalDateTime updatedAt) implements Event {
}
