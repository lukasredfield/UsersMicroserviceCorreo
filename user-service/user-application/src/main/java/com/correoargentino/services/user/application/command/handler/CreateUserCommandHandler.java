package com.correoargentino.services.user.application.command.handler;

import com.correoargentino.services.user.application.command.CreateUserCommand;
import com.correoargentino.services.user.application.messaging.CommandHandler;
import com.correoargentino.services.user.application.port.output.UserRepository;
import com.correoargentino.services.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserCommandHandler implements CommandHandler<CreateUserCommand> {
  private final UserRepository userRepository;

  @Override
  public void handle(CreateUserCommand command) {
    var user = new User();

    user.create(command.id(), command.firstName(),
        command.lastName(), command.emailAddress(), command.phoneNumber());

    userRepository.save(user);
  }
}
