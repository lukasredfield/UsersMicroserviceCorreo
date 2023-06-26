package com.correoargentino.services.user.application.query;

import com.correoargentino.services.user.application.messaging.Query;
import com.correoargentino.services.user.application.query.model.User;
import jakarta.validation.constraints.NotNull;

public record GetUserByUsernameQuery(@NotNull String username) implements Query<User> {
}
