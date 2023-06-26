package com.correoargentino.services.user.presentation.response;

import com.correoargentino.services.user.application.query.model.User;
import java.util.List;

public record SearchUsersResponse(List<User> users) {
}
