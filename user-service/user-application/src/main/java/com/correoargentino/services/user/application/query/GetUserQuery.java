package com.correoargentino.services.user.application.query;

import com.correoargentino.services.user.application.messaging.Query;
import com.correoargentino.services.user.application.query.model.User;
import java.util.UUID;

/**
 * Clase de consulta para obtener un usuario por su ID.
 *
 * Esta clase utiliza un patrón de registro (record) para definir un objeto inmutable con un único campo 'id'.
 * Implementa la interfaz Query<User> para indicar que es una consulta que devuelve un objeto de tipo User.
 *
 * @param id El ID del usuario a obtener.
 */
public record GetUserQuery(UUID id) implements Query<User> {
}
