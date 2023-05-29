package com.correoargentino.services.user.application.query.handler;

import com.correoargentino.services.user.application.exception.UserNotFoundException;
import com.correoargentino.services.user.application.messaging.QueryHandler;
import com.correoargentino.services.user.application.port.output.UserReadRepository;
import com.correoargentino.services.user.application.query.GetUserQuery;
import com.correoargentino.services.user.application.query.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUserQueryHandler implements QueryHandler<GetUserQuery, User> {
  private final UserReadRepository userReadRepository;

  @Override
  public User handle(GetUserQuery query) {
    return userReadRepository.findById(query.id())
        .orElseThrow(() -> new UserNotFoundException(query.id()));
  }
}
