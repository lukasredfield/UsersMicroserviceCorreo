package com.correoargentino.services.user.presentation.mapper;

import com.correoargentino.services.user.application.command.CreateUserCommand;
import com.correoargentino.services.user.application.command.UpdateUserCommand;
import com.correoargentino.services.user.presentation.request.CreateUserRequest;
import com.correoargentino.services.user.presentation.request.UpdateUserRequest;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommandMapper {
  CreateUserCommand fromRequest(CreateUserRequest request);

  @Mapping(target = "id", source = "id")
  UpdateUserCommand fromRequest(UUID id, UpdateUserRequest request);
 }
