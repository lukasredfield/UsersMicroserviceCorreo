package com.correoargentino.services.user.presentation.configuration;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class KeycloakService {

    @Autowired
    private RestTemplate restTemplate;

    private String keycloakUrl = "http://localhost:9090/realms/customers/protocol/openid-connect/token";
    private String clientId = "user-service";
    private String clientSecret = "6E0OMa797-tJPqvE2bny9QoIEqcb_KWLFEgtIvP6U6A";
    private String scope = "openid";
    private String grantType = "password";

    public String login(String username, String password) throws HttpClientErrorException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("grant_type", grantType);
        map.add("client_secret", clientSecret);
        map.add("scope", scope);
        map.add("username", username);
        map.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(keycloakUrl, request, String.class);

        if(response.getStatusCode() == HttpStatus.OK) {
            JSONObject responseBody = new JSONObject(response.getBody());
            String token = responseBody.getString("access_token");
            return token;
        } else {
            throw new HttpClientErrorException(response.getStatusCode(), "Error al autenticar usuario");
        }
    }

}
