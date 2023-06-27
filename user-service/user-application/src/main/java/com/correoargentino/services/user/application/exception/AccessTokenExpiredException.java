package com.correoargentino.services.user.application.exception;

/**
 * Excepción lanzada cuando un token de acceso ha expirado.
 *
 * Esta clase extiende RuntimeException para indicar que es una excepción no verificada.
 */
public class AccessTokenExpiredException extends RuntimeException {
  /**
   * Constructor de la excepción AccessTokenExpiredException.
   *
   * Crea una nueva instancia de la excepción con un mensaje de error predefinido.
   */
  public AccessTokenExpiredException() {
    super("Access token is expired");
  }
}