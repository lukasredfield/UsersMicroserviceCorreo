package com.correoargentino.services.user.application.query;

import com.correoargentino.services.user.application.messaging.Query;
import com.correoargentino.services.user.application.query.model.User;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record GetUserQuery(@NotNull UUID id) implements Query<User> {
}
