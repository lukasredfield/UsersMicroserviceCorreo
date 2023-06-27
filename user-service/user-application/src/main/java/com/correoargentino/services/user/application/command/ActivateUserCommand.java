package com.correoargentino.services.user.application.command;

import com.correoargentino.services.user.application.messaging.Command;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ActivateUserCommand(@NotNull UUID id) implements Command {
}
