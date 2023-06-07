package com.correoargentino.services.user.infrastructure.persistence;

import com.correoargentino.services.user.application.command.UpdateUserCommand;
import com.correoargentino.services.user.application.exception.UserNotFoundException;
import com.correoargentino.services.user.application.port.output.UserRepository;
import com.correoargentino.services.user.domain.model.User;
import com.correoargentino.services.user.infrastructure.persistence.entity.UserEntity;
import com.correoargentino.services.user.infrastructure.persistence.mapper.imp.UserMapperImp;
import com.correoargentino.services.user.infrastructure.persistence.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Creates a new user.
 *
 * @param user The user object to be created.
 */

@Service
@RequiredArgsConstructor
public class UserRepositoryImp implements UserRepository {
  private final UserEntityRepository userEntityRepository;
  private final UserMapperImp userMapperImp;

  @Override
  public Optional<User> find(UUID id) {
    return userEntityRepository.findById(id)
            .map(userMapperImp::toAggregate);
  }


  /**
   * Creates a new user.
   *
   * @param user The user object to be created.
   */

  @Override
  public void create(User user) {
    UserEntity userEntity = userMapperImp.fromAggregate(user);
    userEntityRepository.save(userEntity);
    user.setId(userEntity.getId());
  }

  /**
   * Creates a new user.
   *
   * @param user The user object to be created.
   */

  @Override
  public void updateUser(UpdateUserCommand command) {
    UUID userId = command.id();

    UserEntity existingUserEntity = userEntityRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));

    existingUserEntity.setFirstName(command.firstName());
    existingUserEntity.setLastName(command.lastName());
    existingUserEntity.setEmailAddress(command.emailAddress());
    existingUserEntity.setPhoneNumber(command.phoneNumber());

    userEntityRepository.save(existingUserEntity);
  }

  @Override
  public void delete(UUID id) {
    userEntityRepository.deleteById(id);
  }
}
