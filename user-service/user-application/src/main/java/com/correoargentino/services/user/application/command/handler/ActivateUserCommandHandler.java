package com.correoargentino.services.user.application.command.handler;

import com.correoargentino.services.user.application.command.ActivateUserCommand;
import com.correoargentino.services.user.application.exception.UserNotFoundException;
import com.correoargentino.services.user.application.messaging.CommandHandler;
import com.correoargentino.services.user.application.port.output.UserRepository;
import com.correoargentino.services.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivateUserCommandHandler implements CommandHandler<ActivateUserCommand> {
  private final UserRepository userRepository;
  @Override
  public void handle(ActivateUserCommand command) {
    User user = userRepository.findById(command.id())
        .orElseThrow(() -> new UserNotFoundException(command.id()));

    user.activate();

    userRepository.save(user);
  }
}
