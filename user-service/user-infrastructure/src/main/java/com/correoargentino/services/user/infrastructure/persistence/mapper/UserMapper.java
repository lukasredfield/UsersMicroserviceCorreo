package com.correoargentino.services.user.infrastructure.persistence.mapper;

import com.correoargentino.services.user.domain.model.User;
import com.correoargentino.services.user.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserEntity fromAggregate(User user);

  @InheritInverseConfiguration(name = "fromAggregate")
  User toAggregate(UserEntity userEntity);

  @Mapping(target = "id", ignore = true)
  UserEntity updateFromAggregate(User user, @MappingTarget UserEntity userEntity);

}
