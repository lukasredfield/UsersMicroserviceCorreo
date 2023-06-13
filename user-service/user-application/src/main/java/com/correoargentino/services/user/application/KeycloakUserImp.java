//package com.correoargentino.services.user.application;
//
//import com.correoargentino.services.user.application.exception.UserNotFoundException;
//import com.correoargentino.services.user.application.port.output.KeycloakUser;
//import com.correoargentino.services.user.application.port.output.UserRepository;
//import com.correoargentino.services.user.domain.model.User;
//import org.keycloak.admin.client.Keycloak;
//import org.keycloak.admin.client.KeycloakBuilder;
//import org.keycloak.representations.idm.UserRepresentation;
//
//import javax.ws.rs.core.Response;
//import java.util.Optional;
//import java.util.UUID;
//
//public class KeycloakUserImp implements KeycloakUser {
//
//    private final Keycloak keycloak;
//    private final UserRepository userRepository;
//    private final String realm;
//
//    public KeycloakUserImp(String keycloakUrl, String realm, String clientId, String clientSecret, UserRepository userRepository) {
//        this.keycloak = KeycloakBuilder.builder()
//                .serverUrl(keycloakUrl)
//                .realm(realm)
//                .clientId(clientId)
//                .clientSecret(clientSecret)
//                .build();
//        this.userRepository = userRepository;
//        this.realm = realm;
//    }
//
//    @Override
//    public Optional<User> find(UUID id) throws UserNotFoundException {
//        return userRepository.find(id);
//    }
//
//    @Override
//    public void saveKeycloakUser(User user) {
//        userRepository.save(user);
//
//        UserRepresentation userRepresentation = new UserRepresentation();
//        userRepresentation.setUsername(user.getEmailAddress());
//        userRepresentation.setFirstName(user.getFirstName());
//        userRepresentation.setLastName(user.getLastName());
//        userRepresentation.setEmail(user.getEmailAddress());
//        userRepresentation.setEnabled(true);
//
//        try (Response response = keycloak.realm(realm).users().create(userRepresentation)) {
//            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "\\\\$1");
//            user.setId(UUID.fromString(userId));
//        } catch (Exception e) {
//            String errorMessage = "Error al crear el usuario: " + e.getMessage();
//            e.printStackTrace();
//            throw new RuntimeException(errorMessage);
//        }
//    }
//
//    @Override
//    public void delete(UUID id) {
//        userRepository.delete(id);
//
//        // Eliminar el usuario de Keycloak
//        keycloak.realm(realm).users().delete(id.toString());
//    }
//}
