package com.correoargentino.services.user.application.command;

import com.correoargentino.services.user.application.messaging.Command;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record UpdateUserCommand(
    @NotNull
    UUID id,
    @Size(max = 30)
    String firstName,
    @Size(max = 30)
    String lastName,
    @Email @Size(max = 255)
    String emailAddress,
    @Size(max = 20)
    String phoneNumber) implements Command {
}
