package com.correoargentino.services.user.application.command;

import com.correoargentino.services.user.application.messaging.Command;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateUserCommand(String firstName,
                                String lastName,
                                String emailAddress,
                                String phoneNumber,
                                LocalDateTime createdAt,
                                LocalDateTime updatedAt) implements Command<UUID> {

}
