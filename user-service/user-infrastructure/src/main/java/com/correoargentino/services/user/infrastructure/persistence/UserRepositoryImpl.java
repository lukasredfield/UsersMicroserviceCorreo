package com.correoargentino.services.user.infrastructure.persistence;

import com.correoargentino.services.user.application.port.output.UserRepository;
import com.correoargentino.services.user.domain.model.User;
import com.correoargentino.services.user.infrastructure.persistence.entity.Outbox;
import com.correoargentino.services.user.infrastructure.persistence.mapper.UserMapper;
import com.correoargentino.services.user.infrastructure.persistence.repository.OutboxRepository;
import com.correoargentino.services.user.infrastructure.persistence.repository.UserEntityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
  private final UserEntityRepository userEntityRepository;
  private final OutboxRepository outboxRepository;
  private final UserMapper userMapper;
  private final ObjectMapper objectMapper;

  @Override
  public Optional<User> findById(UUID id) {
    return userEntityRepository.findByIdAndDeletedAtIsNull(id).map(userMapper::toAggregate);
  }

  @Override
  @Transactional
  public void save(User user) {
    userEntityRepository.save(userMapper.fromAggregate(user));

    user.getUncommittedEvents().forEach((event -> {
      var className = event.getClass().getSimpleName();
      var evenType = className.substring(0, className.indexOf("Event"));
      var payload = objectMapper.valueToTree(event);

      final Outbox outbox = Outbox.builder()
          .aggregateType(user.getClass().getSimpleName())
          .aggregateId(user.getId())
          .type(evenType)
          .payload(payload)
          .timestamp(LocalDateTime.now())
          .build();

      outboxRepository.save(outbox);
    }));
  }
}
