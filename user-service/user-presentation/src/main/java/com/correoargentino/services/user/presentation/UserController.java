package com.correoargentino.services.user.presentation;

import com.correoargentino.services.user.application.port.input.UserService;
import com.correoargentino.services.user.application.port.output.KeycloakClient;
import com.correoargentino.services.user.presentation.request.CreateUserRequest;
import com.correoargentino.services.user.presentation.request.UpdateUserRequest;
import com.correoargentino.services.user.presentation.response.CreateUserResponse;
import com.correoargentino.services.user.presentation.response.ErrorResponse;
import com.correoargentino.services.user.presentation.response.GetUserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Clase controladora que maneja las operaciones relacionadas con los usuarios.
 * <p>
 * Esta clase utiliza la anotación @RestController de Spring para indicar que es una clase controladora.
 * Utiliza la anotación @RequestMapping para establecer la ruta base de las solicitudes a "/v1/users" y el tipo de contenido producido como "application/json".
 * Utiliza la anotación @Tag de Swagger para etiquetar esta clase como "Users".
 * La clase también utiliza la anotación @Slf4j de Lombok para la generación automática de un logger.
 * Define métodos para las operaciones GET, POST, PUT y DELETE relacionadas con los usuarios.
 * Los métodos se corresponden con las rutas "/v1/users", "/v1/users/{id}", "/v1/users/{id}", y "/v1/users/{id}" respectivamente.
 * Los métodos se encargan de invocar los métodos correspondientes en el servicio UserService para llevar a cabo las operaciones.
 * Además, la clase tiene dos campos finales para el servicio UserService y el cliente KeycloakClient, y se genera un constructor con argumentos para ellos.
 */
@Slf4j
@RestController
@RequestMapping(value = "v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users")
/*@RequiredArgsConstructor*/
public class UserController {
  private final UserService userService;

  private final KeycloakClient keycloakClient;

  public UserController(UserService userService, KeycloakClient keycloakClient) {
    this.userService = userService;
    this.keycloakClient = keycloakClient;
  }

  /**
   * Dispatch the given {@code command} to the CommandHandler subscribed to the given
   * {@code command}'s name. When the command is processed, one of the callback's methods is called,
   * depending on the result of the processing.
   *
   * @param request The Command to dispatch
   */
  @Operation(
      summary = "Create a user",
      description = "This endpoint creates a user.",
      responses = {
              @ApiResponse(
                      responseCode = "201",
                      description = "Successful operation",
                      content = @Content(
                              schema = @Schema(implementation = CreateUserResponse.class),
                              mediaType = MediaType.APPLICATION_JSON_VALUE)),
              @ApiResponse(
                      responseCode = "400",
                      description = "Bad request",
                      content = @Content(
                              schema = @Schema(implementation = ErrorResponse.class),
                              mediaType = MediaType.APPLICATION_JSON_VALUE)),
              @ApiResponse(
                      responseCode = "500",
                      description = "Service not available",
                      content = @Content(
                              schema = @Schema(implementation = ErrorResponse.class),
                              mediaType = MediaType.APPLICATION_JSON_VALUE))
      }
  )

  @PostMapping
  public ResponseEntity<CreateUserResponse> createUser(
          @Valid @RequestBody CreateUserRequest request) {
    log.info("Creating a new user");
    var id = userService.createUser(request.firstName(),
            request.lastName(), request.emailAddress(), request.phoneNumber(), request.password());
    keycloakClient.register(request.firstName(),request.lastName(),request.emailAddress(),request.password());
    return new ResponseEntity<>(new CreateUserResponse(id), HttpStatus.CREATED);
  }

  /**
   * Dispatch the given {@code command} to the CommandHandler subscribed to the given
   * {@code command}'s name. When the command is processed, one of the callback's methods is called,
   * depending on the result of the processing.
   *
   * @param id The ID of the customer
   */
  @Operation(
      summary = "Get a user",
      description = "Retrieves a user by user ID.",
      parameters = {
          @Parameter(
              name = "id",
              description = "The ID of the user to retrieve, Cannot be empty",
              required = true)
      },
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Successful operation",
              content = @Content(
                  schema = @Schema(implementation = GetUserResponse.class),
                  mediaType = MediaType.APPLICATION_JSON_VALUE)),
          @ApiResponse(
              responseCode = "404",
              description = "User not found",
              content = @Content(
                  schema = @Schema(implementation = ErrorResponse.class),
                  mediaType = MediaType.APPLICATION_JSON_VALUE)),
          @ApiResponse(
              responseCode = "500",
              description = "Service not available",
              content = @Content(
                  schema = @Schema(implementation = ErrorResponse.class),
                  mediaType = MediaType.APPLICATION_JSON_VALUE))
      }
  )
  @GetMapping(value = "{id}")
  public ResponseEntity<GetUserResponse> getUser(@PathVariable UUID id) {
    var user = userService.getUser(id);
    return new ResponseEntity<>(new GetUserResponse(user), HttpStatus.OK);
  }

  /**
   * Dispatch the given {@code command} to the CommandHandler subscribed to the given
   * {@code command}'s name. When the command is processed, one of the callback's methods is called,
   * depending on the result of the processing.
   *
   * @param id The ID of the customer
   */
  @Operation(
      summary = "Update a user",
      description = "This endpoint update a user.",
      parameters = {
          @Parameter(
              name = "id",
              description = "The ID of the user to update, Cannot be empty",
              required = true)
      },
      responses = {
          @ApiResponse(
              responseCode = "204",
              description = "Successful operation"),
          @ApiResponse(
              responseCode = "404",
              description = "User not found",
              content = @Content(
                  schema = @Schema(implementation = ErrorResponse.class),
                  mediaType = MediaType.APPLICATION_JSON_VALUE)),
          @ApiResponse(
              responseCode = "500",
              description = "Service not available",
              content = @Content(
                  schema = @Schema(implementation = ErrorResponse.class),
                  mediaType = MediaType.APPLICATION_JSON_VALUE))
      }
  )

  @PutMapping(value = "{id}")
  public ResponseEntity<Void> updateUser(
          @PathVariable UUID id,
          @RequestBody UpdateUserRequest request) {
    userService.updateUser(id, request.firstName(),
        request.lastName(), request.emailAddress(), request.phoneNumber());
    keycloakClient.updateUser(id, request.firstName(),
            request.lastName(), request.emailAddress());
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * Dispatch the given {@code command} to the CommandHandler subscribed to the given
   * {@code command}'s name. When the command is processed, one of the callback's methods is called,
   * depending on the result of the processing.
   *
   * @param id The ID of the customer
   */
  @Operation(
      summary = "Delete a user",
      description = "This endpoint delete a user.",
      parameters = {
          @Parameter(
              name = "id",
              description = "The ID of the user to delete, Cannot be empty",
              required = true)
      },
      responses = {
          @ApiResponse(
              responseCode = "204",
              description = "Successful operation"),
          @ApiResponse(
              responseCode = "404",
              description = "User not found",
              content = @Content(
                  schema = @Schema(implementation = ErrorResponse.class),
                  mediaType = MediaType.APPLICATION_JSON_VALUE)),
          @ApiResponse(
              responseCode = "500",
              description = "Service not available",
              content = @Content(
                  schema = @Schema(implementation = ErrorResponse.class),
                  mediaType = MediaType.APPLICATION_JSON_VALUE))
      }
  )
  @DeleteMapping(value = "{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
    userService.deleteUser(id);
    keycloakClient.deleteUser(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
