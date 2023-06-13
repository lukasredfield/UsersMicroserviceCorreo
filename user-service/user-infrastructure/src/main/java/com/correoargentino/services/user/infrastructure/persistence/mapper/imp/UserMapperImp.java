package com.correoargentino.services.user.infrastructure.persistence.mapper.imp;

import com.correoargentino.services.user.domain.model.User;
import com.correoargentino.services.user.infrastructure.integration.KeycloakClientImpl;
import com.correoargentino.services.user.infrastructure.persistence.entity.UserEntity;
import com.correoargentino.services.user.domain.model.UserKeycloak;
import com.correoargentino.services.user.infrastructure.persistence.mapper.UserMapper;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@NoArgsConstructor
public class UserMapperImp implements UserMapper {
    @Autowired
    private KeycloakClientImpl keycloakClientImp;

    @Override
    public UserEntity fromAggregate(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(user.getUserName());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmailAddress(user.getEmailAddress());
        userEntity.setPassword(user.getPassword());
        userEntity.setCreatedAt(user.getCreatedAt());
        userEntity.setUpdatedAt(user.getUpdatedAt());

        return userEntity;
    }

    public UserEntity fromAggregateKeycloak(UserEntity userEntity, UserKeycloak userKeycloak) {
        userEntity.setUserName(userKeycloak.getUserName());
        userEntity.setFirstName(userKeycloak.getFirstName());
        userEntity.setLastName(userKeycloak.getLastName());
        userEntity.setEmailAddress(userKeycloak.getEmailAddress());

        if (userKeycloak.getId() != null) {

            UUID id = UUID.randomUUID();
            userEntity.setId(id);
        }

        keycloakClientImp.createKeycloakUser(userKeycloak);

        return userEntity;
    }



    @Override
    public User toAggregate(UserEntity userEntity) {
        return new User(
                userEntity.getUserName(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmailAddress(),
                userEntity.getPassword(),
                userEntity.getCreatedAt(),
                userEntity.getUpdatedAt()
        );
    }

}
