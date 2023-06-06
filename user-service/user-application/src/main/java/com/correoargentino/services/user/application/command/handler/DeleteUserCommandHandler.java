package com.correoargentino.services.user.application.command.handler;

import com.correoargentino.services.user.application.command.DeleteUserCommand;
import com.correoargentino.services.user.application.messaging.CommandHandler;
import com.correoargentino.services.user.application.port.output.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Qualifier("deleteUserCommandHandler")
public class DeleteUserCommandHandler implements CommandHandler<DeleteUserCommand, Void> {
    private final UserRepository userRepository;

    public DeleteUserCommandHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Void handle(DeleteUserCommand command) {
        UUID userId = command.id();

        userRepository.delete(userId);

        return null;
    }
}
