package com.correoargentino.services.user.infrastructure.persistence.mapper;

import com.correoargentino.services.user.domain.model.User;
import com.correoargentino.services.user.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Interfaz que define la funcionalidad de mapeo entre entidades UserEntity y objetos User.
 * <p>
 * Esta interfaz utiliza la anotación @Mapper de MapStruct con el componenteModel "spring" para indicar que es un componente administrado por Spring y para generar automáticamente la implementación del mapeo.
 * Define dos métodos de mapeo: fromAggregate() y toAggregate().
 * El método fromAggregate() mapea un objeto User a una entidad UserEntity.
 * El método toAggregate() mapea una entidad UserEntity a un objeto User.
 * Utiliza la anotación @Mapping para configurar el mapeo de los campos correspondientes, donde el campo "emailAddress" de User se asigna al campo "username" de UserEntity.
 * También utiliza la anotación @InheritInverseConfiguration para indicar que el método toAggregate() hereda la configuración de mapeo inverso del método fromAggregate().
 */

@Mapper(componentModel = "spring")
public interface UserMapper {
  @Mapping(target = "username", source = "emailAddress")
  UserEntity fromAggregate(User user);

  @InheritInverseConfiguration(name = "fromAggregate")
  User toAggregate(UserEntity userEntity);
}
