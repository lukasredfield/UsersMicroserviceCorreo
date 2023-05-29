package com.correoargentino.services.user.application.command.handler;

import com.correoargentino.services.user.application.command.CreateUserCommand;
import com.correoargentino.services.user.application.messaging.CommandHandler;
import com.correoargentino.services.user.application.port.output.UserRepository;
import com.correoargentino.services.user.domain.model.User;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUsertCommandHandler implements CommandHandler<CreateUserCommand, UUID> {
  private final UserRepository userRepository;

  @Override
  public UUID handle(CreateUserCommand command) {
    var user = new User(UUID.randomUUID());
    userRepository.save(user);
    return user.getId();
  }
}
