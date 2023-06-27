package com.correoargentino.services.user.application.query.handler;

import com.correoargentino.services.user.application.exception.UserNotFoundException;
import com.correoargentino.services.user.application.messaging.QueryHandler;
import com.correoargentino.services.user.application.port.output.UserReadRepository;
import com.correoargentino.services.user.application.query.GetUserQuery;
import com.correoargentino.services.user.application.query.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Manejador de consulta para obtener un usuario.
 *
 * Implementa la interfaz QueryHandler para procesar la consulta GetUserQuery y devolver el usuario correspondiente.
 */
@Component
@RequiredArgsConstructor
public class GetUserQueryHandler implements QueryHandler<GetUserQuery, User> {
  private final UserReadRepository userReadRepository;

  /**
   * Maneja la consulta GetUserQuery y devuelve el usuario correspondiente.
   *
   * @param query La consulta GetUserQuery.
   * @return El usuario correspondiente a la consulta.
   * @throws UserNotFoundException Si no se encuentra ningún usuario con el ID especificado en la consulta.
   */
  @Override
  public User handle(GetUserQuery query) {
    return userReadRepository.findById(query.id())
            .orElseThrow(() -> new UserNotFoundException(query.id()));
  }
}



