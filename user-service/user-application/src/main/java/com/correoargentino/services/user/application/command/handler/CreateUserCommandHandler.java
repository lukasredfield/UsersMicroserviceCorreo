package com.correoargentino.services.user.application.command.handler;

import com.correoargentino.services.user.application.command.CreateUserCommand;
import com.correoargentino.services.user.application.messaging.CommandHandler;
import com.correoargentino.services.user.application.port.output.UserRepository;
import com.correoargentino.services.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserCommandHandler implements CommandHandler<CreateUserCommand> {
  private final UserRepository userRepository;

  @Override
  public Void handle(CreateUserCommand command) {
    var user = User.create(command.id(), command.firstName(),
        command.lastName(), command.emailAddress(), command.phoneNumber());

    userRepository.save(user);

    return null;
  }
}
