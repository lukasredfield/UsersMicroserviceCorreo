package com.correoargentino.services.user.infrastructure.integration;

import com.correoargentino.services.user.infrastructure.persistence.entity.UserEntity;
import com.correoargentino.services.user.domain.model.UserKeycloak;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;

@Service
public class KeycloakClientImpl implements KeycloakClient {
    private final Keycloak keycloakClient;

    public KeycloakClientImpl() {
        this.keycloakClient = KeycloakBuilder.builder()
                .serverUrl("http://localhost:9090/auth")
                .realm("customers")
                .clientId("user-service")
                .clientSecret("6E0OMa797-tJPqvE2bny9QoIEqcb_KWLFEgtIvP6U6A")
                .grantType(OAuth2Constants.PASSWORD)
                .username("admin")
                .password("admin")
                .build();
    }

    public void createKeycloakUser(UserKeycloak userKeycloak) {

        try {
            UserRepresentation userRepresentation = new UserRepresentation();
            userRepresentation.setUsername(userKeycloak.getUserName());
            userRepresentation.setFirstName(userKeycloak.getFirstName());
            userRepresentation.setLastName(userKeycloak.getLastName());
            userRepresentation.setEmail(userKeycloak.getEmailAddress());

            RealmResource realmResource = keycloakClient.realm("customers");
            UsersResource usersResource = realmResource.users();
            Response response = usersResource.create(userRepresentation);


            if (response.getStatus() == HttpStatus.CREATED.value()) {

            } else {
            }
        } catch (Exception e) {

        }
    }

    public UserKeycloak getUserKeycloak(UserEntity userEntity) {
        UserKeycloak userKeycloak = new UserKeycloak();
        userKeycloak.setId(userEntity.getId().toString());
        userKeycloak.setUserName(userEntity.getUserName());
        userKeycloak.setFirstName(userEntity.getFirstName());
        userKeycloak.setLastName(userEntity.getLastName());
        userKeycloak.setEmailAddress(userEntity.getEmailAddress());
        return userKeycloak;
    }
}


//    private final RestTemplate restTemplate;
//
//    @Value("${keycloak.logout}")
//    private String keycloakLogout;
//
//    @Value("${keycloak.user-info-uri}")
//    private String keycloakUserInfo;
//
//    @Value("${keycloak.client-id}")
//    private String clientId;
//
//    @Value("${keycloak.client-secret}")
//    private String clientSecret;
//
//    public void logout(String refreshToken) {
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("client_id",clientId);
//        map.add("client_secret",clientSecret);
//        map.add("refresh_token",refreshToken);
//
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, null);
//        restTemplate.postForObject(keycloakLogout, request, String.class);
//    }
//
//    public String getUserInfo(String token) {
//        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//        headers.add("Authorization", token);
//
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
//        return restTemplate.postForObject(keycloakUserInfo, request, String.class);
//    }

