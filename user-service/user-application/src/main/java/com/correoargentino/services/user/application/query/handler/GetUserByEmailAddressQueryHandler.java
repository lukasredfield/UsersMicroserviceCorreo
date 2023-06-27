package com.correoargentino.services.user.application.query.handler;

import com.correoargentino.services.user.application.exception.EmailAddressNotFoundException;
import com.correoargentino.services.user.application.messaging.QueryHandler;
import com.correoargentino.services.user.application.port.output.UserQuery;
import com.correoargentino.services.user.application.query.GetUserByEmailAddressQuery;
import com.correoargentino.services.user.application.query.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserByEmailAddressQueryHandler
    implements QueryHandler<GetUserByEmailAddressQuery, User> {
  private final UserQuery userQuery;

  @Override
  public User handle(GetUserByEmailAddressQuery query) {
    return userQuery.getByEmailAddress(query.emailAddress())
        .orElseThrow(() -> new EmailAddressNotFoundException(query.emailAddress()));
  }
}
