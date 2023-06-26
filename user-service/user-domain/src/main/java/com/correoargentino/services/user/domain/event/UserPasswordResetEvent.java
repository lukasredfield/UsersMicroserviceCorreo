package com.correoargentino.services.user.domain.event;

import com.correoargentino.services.user.domain.primitive.Event;
import java.time.LocalDateTime;

public record UserPasswordResetEvent(LocalDateTime resetAt) implements Event {
}

