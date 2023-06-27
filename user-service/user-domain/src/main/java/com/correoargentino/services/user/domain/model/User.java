package com.correoargentino.services.user.domain.model;

import com.correoargentino.services.user.domain.event.UserCreatedEvent;
import com.correoargentino.services.user.domain.event.UserDeletedEvent;
import com.correoargentino.services.user.domain.event.UserUpdatedEvent;
import com.correoargentino.services.user.domain.primitive.AggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


/**
 * Clase que representa a un usuario.
 * <p>
 * Esta clase hereda de AggregateRoot<UUID> para indicar que es una raíz de agregado con un identificador de tipo UUID.
 * Contiene campos para el nombre de usuario, nombre, apellido, dirección de correo electrónico, número de teléfono,
 * preferencias, fecha de eliminación y métodos para crear, actualizar y eliminar un usuario.
 * También implementa métodos para manejar eventos relacionados con el usuario.
 * Los métodos equals() y hashCode() se han sobrescrito para realizar comparaciones de igualdad basadas en los campos de la clase.
 */
@Getter
@Setter
public class User extends AggregateRoot<UUID> {

  private String username;
  private String firstName;
  private String lastName;
  private String emailAddress;
  private String phoneNumber;
  private Preferences preferences;
  private LocalDateTime deletedAt;


  /**
   * Método que crea un usuario.
   * <p>
   * Este método toma como parámetros el ID del usuario, nombre, apellido, dirección de correo electrónico y número de teléfono.
   * Aplica un evento UserCreatedEvent para indicar que se ha creado un nuevo usuario.
   */
  public void create(UUID id, String firstName,
                     String lastName, String emailAddress, String phoneNumber) {
    apply(new UserCreatedEvent(id, emailAddress,
            firstName, lastName, emailAddress, phoneNumber, LocalDateTime.now()));
  }

  /**
   * Método que actualiza los detalles de un usuario.
   * <p>
   * Este método toma como parámetros el nuevo nombre, apellido, dirección de correo electrónico y número de teléfono del usuario.
   * Aplica un evento UserUpdatedEvent para indicar que se han actualizado los detalles del usuario.
   */
  public void update(String firstName, String lastName, String emailAddress, String phoneNumber) {
    apply(new UserUpdatedEvent(
            firstName, lastName, emailAddress, phoneNumber, LocalDateTime.now()));
  }

  /**
   * Método que elimina un usuario.
   * <p>
   * Aplica un evento UserDeletedEvent para indicar que se ha eliminado el usuario.
   */
  public void delete() {
    apply(new UserDeletedEvent(LocalDateTime.now()));
  }

  /**
   * Método que maneja el evento UserCreatedEvent.
   * <p>
   * Este método se llama cuando se produce un evento UserCreatedEvent y actualiza los campos del usuario en base al evento.
   *
   * @param event El evento UserCreatedEvent.
   */
  public void onEvent(UserCreatedEvent event) {
    this.id = event.id();
    this.username = event.username();
    this.firstName = event.firstName();
    this.lastName = event.lastName();
    this.emailAddress = event.emailAddress();
    this.phoneNumber = event.phoneNumber();
    this.createdAt = event.createdAt();
    this.updatedAt = event.createdAt();
  }

  /**
   * Método que maneja el evento UserUpdatedEvent.
   * <p>
   * Este método se llama cuando se produce un evento UserUpdatedEvent y actualiza los campos del usuario en base al evento.
   *
   * @param event El evento UserUpdatedEvent.
   */
  public void onEvent(UserUpdatedEvent event) {
    this.firstName = event.firstName();
    this.lastName = event.lastName();
    this.emailAddress = event.emailAddress();
    this.phoneNumber = event.phoneNumber();
    this.updatedAt = event.updatedAt();
  }

  /**
   * Método que maneja el evento UserDeletedEvent.
   * <p>
   * Este método se llama cuando se produce un evento UserDeletedEvent y actualiza el campo de fecha de eliminación del usuario en base al evento.
   *
   * @param event El evento UserDeletedEvent.
   */
  public void onEvent(UserDeletedEvent event) {
    this.deletedAt = event.deletedAt();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(firstName, user.firstName) &&
            Objects.equals(lastName, user.lastName) &&
            Objects.equals(emailAddress, user.emailAddress) &&
            Objects.equals(phoneNumber, user.phoneNumber) &&
            Objects.equals(preferences, user.preferences);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(),
            firstName, lastName, emailAddress, phoneNumber, preferences);
  }
}


