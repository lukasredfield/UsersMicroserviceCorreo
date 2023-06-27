package com.correoargentino.services.user.infrastructure.persistence;

import com.correoargentino.services.user.application.port.output.UserRepository;
import com.correoargentino.services.user.domain.model.User;
import com.correoargentino.services.user.infrastructure.persistence.entity.Outbox;
import com.correoargentino.services.user.infrastructure.persistence.mapper.UserMapper;
import com.correoargentino.services.user.infrastructure.persistence.repository.OutboxRepository;
import com.correoargentino.services.user.infrastructure.persistence.repository.UserEntityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementación de UserRepository que interactúa con la base de datos para realizar operaciones relacionadas con los usuarios.
 * <p>
 * Esta clase utiliza la anotación @Service de Spring para indicar que es un servicio.
 * Utiliza la anotación @RequiredArgsConstructor de Lombok para generar un constructor con argumentos para los campos marcados con 'final'.
 * Implementa los métodos findById(), save() y delete() de UserRepository para buscar, guardar y eliminar usuarios respectivamente.
 * Utiliza instancias de UserEntityRepository, OutboxRepository, UserMapper y ObjectMapper para interactuar con la base de datos y mapear entidades y objetos.
 */

@Service
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
  private final UserEntityRepository userEntityRepository;
  private final OutboxRepository outboxRepository;
  private final UserMapper userMapper;
  private final ObjectMapper objectMapper;

  /**
   * Método que busca un usuario por su ID en la base de datos.
   *
   * @param id El ID del usuario a buscar.
   * @return Un Optional que contiene el usuario encontrado, o un Optional vacío si no se encontró ningún usuario con el ID especificado.
   */
  @Override
  public Optional<User> findById(UUID id) {
    return userEntityRepository.findByIdAndDeletedAtIsNull(id).map(userMapper::toAggregate);
  }


  /**
   * Método que guarda un usuario en la base de datos.
   *
   * @param user El usuario a guardar.
   */
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


  /**
   * Método que elimina un usuario de la base de datos.
   *
   * @param user El usuario a eliminar.
   */
  @Override
  public void delete(User user) {
    userEntityRepository.delete(userMapper.fromAggregate(user));

  }
}
