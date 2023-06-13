package com.correoargentino.services.user.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserKeycloak {

    private String id;
    private String userName;
    private String firstName;
    private String lastName;
    private String emailAddress;

}
