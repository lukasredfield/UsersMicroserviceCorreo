package com.correoargentino.services.user.infrastructure.persistence.repository;

import com.correoargentino.services.user.infrastructure.persistence.entity.UserEntity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends CrudRepository<UserEntity, UUID> {
}
