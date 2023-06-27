package com.correoargentino.services.user.application.command.handler;

import com.correoargentino.services.user.application.command.DeleteUserCommand;
import com.correoargentino.services.user.application.exception.UserNotFoundException;
import com.correoargentino.services.user.application.messaging.CommandHandler;
import com.correoargentino.services.user.application.port.output.UserRepository;
import com.correoargentino.services.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserCommandHandler implements CommandHandler<DeleteUserCommand> {
  private final UserRepository userRepository;

  @Override
  public void handle(DeleteUserCommand command) {
    User user = userRepository.findById(command.id())
        .orElseThrow(() -> new UserNotFoundException(command.id()));

    user.delete();

    userRepository.save(user);
  }
}
