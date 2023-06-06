package com.correoargentino.services.user.application.command.handler;

import com.correoargentino.services.user.application.command.CreateUserCommand;
import com.correoargentino.services.user.application.messaging.CommandHandler;
import com.correoargentino.services.user.application.port.output.UserRepository;
import com.correoargentino.services.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateUsertCommandHandler implements CommandHandler<CreateUserCommand, UUID> {
    private final UserRepository userRepository;

    @Override
    public UUID handle(CreateUserCommand command) {
        var user = new User(
                command.firstName(),
                command.lastName(),
                command.emailAddress(),
                command.phoneNumber(),
                command.createdAt(),
                command.updatedAt()
        );
        userRepository.create(user);
        return user.getId();
    }
}
