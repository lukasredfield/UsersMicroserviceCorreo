package com.correoargentino.services.user.presentation;

import com.correoargentino.services.user.presentation.configuration.KeycloakService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@Controller
public class LoginController {

    @Autowired
    private KeycloakService keycloakService;

    @PostMapping("/login")
    public ResponseEntity<Object> submitLoginForm(@RequestBody LoginForm loginForm) {
        try {
            String token = keycloakService.login(loginForm.getUsername(), loginForm.getPassword());
            JSONObject response = new JSONObject();
            response.put("access_token", token);
            return ResponseEntity.ok(response.toString());
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }


    public static class LoginForm {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
