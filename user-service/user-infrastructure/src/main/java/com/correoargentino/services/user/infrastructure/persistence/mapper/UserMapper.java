package com.correoargentino.services.user.infrastructure.persistence.mapper;

import com.correoargentino.services.user.domain.model.User;
import com.correoargentino.services.user.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
  @Mapping(target = "username", source = "emailAddress")
  UserEntity fromAggregate(User user);

  @InheritInverseConfiguration(name = "fromAggregate")
  User toAggregate(UserEntity userEntity);
}
