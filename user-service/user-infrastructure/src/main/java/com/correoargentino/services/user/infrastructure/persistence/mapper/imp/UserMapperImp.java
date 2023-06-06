package com.correoargentino.services.user.infrastructure.persistence.mapper.imp;

import com.correoargentino.services.user.domain.model.User;
import com.correoargentino.services.user.infrastructure.persistence.entity.UserEntity;
import com.correoargentino.services.user.infrastructure.persistence.mapper.UserMapper;
import org.springframework.stereotype.Component;

/**
 * Creates a new user.
 *
 * @param user The user object to be created.
 */

@Component
public class UserMapperImp implements UserMapper {

    @Override
    public UserEntity fromAggregate(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmailAddress(user.getEmailAddress());
        userEntity.setPhoneNumber(user.getPhoneNumber());
        userEntity.setCreatedAt(user.getCreatedAt());
        userEntity.setUpdatedAt(user.getUpdatedAt());
        return userEntity;
    }

    @Override
    public User toAggregate(UserEntity userEntity) {
        return new User(
                userEntity.getFirstName(),
                userEntity.getFirstName(),
                userEntity.getEmailAddress(),
                userEntity.getPhoneNumber(),
                userEntity.getCreatedAt(),
                userEntity.getUpdatedAt()
        );
    }

}
