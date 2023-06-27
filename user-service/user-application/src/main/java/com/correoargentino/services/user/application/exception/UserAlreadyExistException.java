package com.correoargentino.services.user.application.exception;

/**
 * Excepción lanzada cuando ya existe un usuario con el nombre de usuario proporcionado.
 *
 * Esta clase extiende RuntimeException para indicar que es una excepción no verificada.
 */
public class UserAlreadyExistException extends RuntimeException {
  /**
   * Constructor de la excepción UserAlreadyExistException.
   *
   * Crea una nueva instancia de la excepción con un mensaje de error personalizado que indica que ya existe un usuario con el nombre de usuario proporcionado.
   *
   * @param username El nombre de usuario que ya existe.
   */
  public UserAlreadyExistException(String username) {
    super("User with the username " + username + " already exists!");
  }
}

