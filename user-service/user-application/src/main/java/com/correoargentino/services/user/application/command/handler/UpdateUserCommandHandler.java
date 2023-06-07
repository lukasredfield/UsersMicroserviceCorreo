package com.correoargentino.services.user.application.command.handler;

import com.correoargentino.services.user.application.command.UpdateUserCommand;
import com.correoargentino.services.user.application.exception.UserNotFoundException;
import com.correoargentino.services.user.application.messaging.CommandHandler;
import com.correoargentino.services.user.application.port.output.UserRepository;
import com.correoargentino.services.user.domain.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@Qualifier("updateUserCommandHandler")
public class UpdateUserCommandHandler implements CommandHandler<UpdateUserCommand, Void> {
    private final UserRepository userRepository;

    public UpdateUserCommandHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Void handle(UpdateUserCommand command) {
        UUID userId = command.id();

        User user = userRepository.find(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setFirstName(command.firstName());
        user.setLastName(command.lastName());
        user.setEmailAddress(command.emailAddress());
        user.setPhoneNumber(command.phoneNumber());

        userRepository.updateUser(command);

        return null;
    }


}