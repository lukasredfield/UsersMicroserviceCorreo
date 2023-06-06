package com.correoargentino.services.user.presentation.request;



public record UpdateUserRequest(String firstName,
                                String lastName,
                                String emailAddress,
                                String phoneNumber)
    {
}
