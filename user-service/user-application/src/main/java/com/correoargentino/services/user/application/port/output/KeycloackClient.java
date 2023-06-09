package com.correoargentino.services.user.application.port.output;


public interface KeycloackClient {

    public void logout(String refreshToken);

    public String getUserInfo(String token);
}
