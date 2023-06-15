package com.correoargentino.services.user.application.query;

import com.correoargentino.services.user.application.messaging.Query;
import com.correoargentino.services.user.application.query.model.User;
import java.util.UUID;

public record GetUserQuery(UUID id) implements Query<User> {
}
