package com.correoargentino.services.user.application.query.handler;

import com.correoargentino.services.user.application.exception.UsernameNotFoundException;
import com.correoargentino.services.user.application.messaging.QueryHandler;
import com.correoargentino.services.user.application.port.output.UserQuery;
import com.correoargentino.services.user.application.query.GetUserByUsernameQuery;
import com.correoargentino.services.user.application.query.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserByUsernameQueryHandler implements QueryHandler<GetUserByUsernameQuery, User> {
  private final UserQuery userQuery;

  @Override
  public User handle(GetUserByUsernameQuery query) {
    return userQuery.getByUsername(query.username())
        .orElseThrow(() -> new UsernameNotFoundException(query.username()));
  }
}
