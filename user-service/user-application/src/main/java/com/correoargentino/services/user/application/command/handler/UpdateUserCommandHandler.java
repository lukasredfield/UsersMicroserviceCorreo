package com.correoargentino.services.user.application.command.handler;

import com.correoargentino.services.user.application.command.UpdateUserCommand;
import com.correoargentino.services.user.application.exception.UserNotFoundException;
import com.correoargentino.services.user.application.messaging.CommandHandler;
import com.correoargentino.services.user.application.port.output.UserRepository;
import com.correoargentino.services.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserCommandHandler implements CommandHandler<UpdateUserCommand> {
  private final UserRepository userRepository;

  @Override
  public Void handle(UpdateUserCommand command) {
    User user = userRepository.find(command.id())
        .orElseThrow(() -> new UserNotFoundException(command.id()));

    user.update(command.firstName(),
        command.lastName(), command.emailAddress(), command.phoneNumber());

    userRepository.save(user);

    return null;
  }
}
