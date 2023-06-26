package com.correoargentino.services.user.application.port.input;

import com.correoargentino.services.user.application.query.model.User;
import java.util.List;
import java.util.UUID;

public interface UserService {

  List<User> searchUsers(String query);

  UUID createUser(String firstName, String lastName,
                  String emailAddress, String phoneNumber, String password);

  User getUser(UUID id);

  void updateUser(UUID id, String firstName,
                  String lastName, String emailAddress, String phoneNumber);

  void deleteUser(UUID id);

  void verifyUser(String username);

  void activateUser(String token);

  void changeUserPassword(UUID id, String password);
}