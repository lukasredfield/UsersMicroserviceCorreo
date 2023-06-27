package com.correoargentino.services.user.application.query;

import com.correoargentino.services.user.application.messaging.Query;
import com.correoargentino.services.user.application.query.model.User;
import jakarta.validation.constraints.Email;

public record GetUserByEmailAddressQuery(@Email String emailAddress) implements Query<User> {
}
