package com.correoargentino.services.user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserKeycloak {

    private String id;
    private String userName;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
