package com.correoargentino.services.user.application.command.handler;

import com.correoargentino.services.user.application.command.CreateUserCommand;
import com.correoargentino.services.user.application.messaging.CommandHandler;
import com.correoargentino.services.user.application.port.output.UserRepository;
import com.correoargentino.services.user.domain.model.User;
import com.correoargentino.services.user.domain.model.UserKeycloak;
import java.time.LocalDateTime;
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
                // id,
                command.userName(),
                command.firstName(),
                command.lastName(),
                command.emailAddress(),
                command.password(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        var userKeycloak = new UserKeycloak();

        userRepository.save(user);
        userRepository.safe(userKeycloak);

        return user.getId();
    }
}
