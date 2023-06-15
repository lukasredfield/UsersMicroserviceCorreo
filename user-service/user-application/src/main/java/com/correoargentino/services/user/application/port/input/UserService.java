package com.correoargentino.services.user.application.port.input;

import com.correoargentino.services.user.application.query.model.User;
import java.util.UUID;

public interface UserService {
  UUID createUser(String firstName, String lastName,
                  String emailAddress, String phoneNumber, String password);

  void updateUser(UUID id, String firstName,
                  String lastName, String emailAddress, String phoneNumber);

  void deleteUser(UUID id);

  User getUser(UUID id);
}