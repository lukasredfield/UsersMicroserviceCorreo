package com.correoargentino.services.user.infrastructure.persistence;

import com.correoargentino.services.user.application.port.output.UserRepository;
import com.correoargentino.services.user.domain.model.User;
import com.correoargentino.services.user.infrastructure.persistence.mapper.UserMapper;
import com.correoargentino.services.user.infrastructure.persistence.repository.UserEntityRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
  private final UserEntityRepository userEntityRepository;
  private final UserMapper userMapper;

  @Override
  public Optional<User> find(UUID id) {
    return userEntityRepository.findById(id).map(userMapper::toAggregate);
  }

  public void save(User user) {
    userEntityRepository.save(userMapper.fromAggregate(user));
  }

  @Override
  public void delete(UUID id) {
    userEntityRepository.deleteById(id);
  }
}
