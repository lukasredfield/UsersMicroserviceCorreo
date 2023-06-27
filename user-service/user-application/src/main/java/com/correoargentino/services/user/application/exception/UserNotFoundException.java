package com.correoargentino.services.user.application.exception;

import java.util.UUID;

/**
 * Excepción lanzada cuando no se encuentra un usuario con el ID proporcionado.
 *
 * Esta clase extiende RuntimeException para indicar que es una excepción no verificada.
 */
public class UserNotFoundException extends RuntimeException {
  /**
   * Constructor de la excepción UserNotFoundException.
   *
   * Crea una nueva instancia de la excepción con un mensaje de error personalizado que indica que no se encontró un usuario con el ID proporcionado.
   *
   * @param userId El ID del usuario que no se encontró.
   */
  public UserNotFoundException(UUID userId) {
    super("User with id " + userId.toString() + " was not found!");
  }
}

