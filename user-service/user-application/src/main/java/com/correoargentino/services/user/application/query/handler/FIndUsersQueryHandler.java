package com.correoargentino.services.user.application.query.handler;

import com.correoargentino.services.user.application.messaging.QueryHandler;
import com.correoargentino.services.user.application.port.output.UserQuery;
import com.correoargentino.services.user.application.query.FindUsersQuery;
import com.correoargentino.services.user.application.query.model.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FIndUsersQueryHandler implements QueryHandler<FindUsersQuery, List<User>> {
  private final UserQuery userQuery;

  @Override
  public List<User> handle(FindUsersQuery query) {
    return userQuery.findUsers();
  }
}
