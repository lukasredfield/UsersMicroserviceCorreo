package com.correoargentino.services.user.application.command;

import com.correoargentino.services.user.application.messaging.Command;
import java.util.UUID;

public record UpdateUserCommand(UUID id, String name) implements Command<Void> {
}
