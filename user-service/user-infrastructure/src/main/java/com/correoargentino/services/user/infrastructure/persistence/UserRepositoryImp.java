package com.correoargentino.services.user.infrastructure.persistence;

import com.correoargentino.services.user.application.port.output.UserRepository;
import com.correoargentino.services.user.domain.model.User;
import com.correoargentino.services.user.infrastructure.persistence.mapper.imp.UserMapperImp;
import com.correoargentino.services.user.infrastructure.persistence.repository.UserEntityRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRepositoryImp implements UserRepository {
  private final UserEntityRepository userEntityRepository;
  private final UserMapperImp userMapperImp;

  @Override
  public Optional<User> find(UUID id) {
    return userEntityRepository.findById(id).map(userMapperImp::toAggregate);
  }


  public void save(User user) {
    userEntityRepository.save(userMapperImp.fromAggregate(user));
  }

  /**
   * Creates a new user.
   *
   * @param id The user object to be created.
   */
  @Override
  public void delete(UUID id) {
    userEntityRepository.deleteById(id);
  }
}
