package com.correoargentino.services.user.infrastructure.persistence.mapper.imp;

import com.correoargentino.services.user.domain.model.User;
import com.correoargentino.services.user.infrastructure.integration.KeycloakClientImpl;
import com.correoargentino.services.user.infrastructure.persistence.entity.UserEntity;
import com.correoargentino.services.user.infrastructure.persistence.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class UserMapperImp implements UserMapper {
    @Autowired
    private KeycloakClientImpl keycloakClientImp;

    public UserEntity fromAggregate(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmailAddress(user.getEmailAddress());
        userEntity.setCreatedAt(user.getCreatedAt());
        userEntity.setUpdatedAt(user.getUpdatedAt());
        return userEntity;
    }

    @Override
    public User toAggregate(UserEntity userEntity) {
        return null;
    }

//    public User toAggregate(UserEntity userEntity) {
//        return new User(userEntity.getFirstName(), userEntity.getLastName(), userEntity.getEmailAddress(), userEntity.getPhoneNumber(), userEntity.getCreatedAt());
//    }

}

