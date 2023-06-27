package com.correoargentino.services.user.presentation;

import com.correoargentino.services.user.application.port.input.UserService;
import com.correoargentino.services.user.presentation.request.CreateUserRequest;
import com.correoargentino.services.user.presentation.request.RecoveryUserPasswordRequest;
import com.correoargentino.services.user.presentation.request.ResetUserPasswordRequest;
import com.correoargentino.services.user.presentation.request.UpdateUserRequest;
import com.correoargentino.services.user.presentation.request.VerifyUserRequest;
import com.correoargentino.services.user.presentation.response.CreateUserResponse;
import com.correoargentino.services.user.presentation.response.ErrorResponse;
import com.correoargentino.services.user.presentation.response.GetUserResponse;
import com.correoargentino.services.user.presentation.response.SearchUsersResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Claudio Amoedo
 * @since 0.1
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  /**
   * Retrieve details of users.
   *
   * @param query The query parameter can be used to get users that match the specified criteria.
   */
  @Operation(
      summary = "Search Users",
      description = "Retrieve details of users.",
      parameters = {
          @Parameter(
              name = "query",
              description =
                  "The query parameter can be used to get users that match the specified criteria."
          )
      },
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Successful operation",
              content = @Content(
                  schema = @Schema(implementation = GetUserResponse.class),
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
  @GetMapping
  public ResponseEntity<SearchUsersResponse> searchUsers(
      @RequestParam(required = false) String query) {
    var users = userService.searchUsers(query);
    return new ResponseEntity<>(new SearchUsersResponse(users), HttpStatus.OK);
  }

  /**
   * Create a new user.
   *
   * @param request The Command to dispatch
   */
  @Operation(
      summary = "Create a User",
      description = "Create a new user.",
      responses = {
          @ApiResponse(
              responseCode = "201",
              description = "Successful operation",
              content = @Content(
                  schema = @Schema(implementation = CreateUserResponse.class),
                  mediaType = MediaType.APPLICATION_JSON_VALUE)),
          @ApiResponse(
              responseCode = "400",
              description = "User already exists",
              content = @Content(
                  schema = @Schema(implementation = ErrorResponse.class),
                  mediaType = MediaType.APPLICATION_JSON_VALUE)),
          @ApiResponse(
              responseCode = "409",
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
    return new ResponseEntity<>(new CreateUserResponse(id), HttpStatus.CREATED);
  }

  /**
   * Retrieve user details. A list of fields to include or exclude may also be specified.
   *
   * @param id The ID of the user to retrieve, Cannot be empty.
   */
  @Operation(
      summary = "Get a User",
      description =
          "Retrieve user details. A list of fields to include or exclude may also be specified.",
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
   * Update a user.
   *
   * @param id ID of the user to update, Cannot be empty
   */
  @Operation(
      summary = "Update a User",
      description = "Update a user.",
      parameters = {
          @Parameter(
              name = "id",
              description = "ID of the user to update, Cannot be empty",
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

  @PatchMapping(value = "{id}")
  public ResponseEntity<Void> updateUser(
      @PathVariable UUID id, @Valid @RequestBody UpdateUserRequest request) {
    userService.updateUser(id, request.firstName(),
        request.lastName(), request.emailAddress(), request.phoneNumber());
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * Delete a user.
   *
   * @param id ID of the user to delete, Cannot be empty
   */
  @Operation(
      summary = "Delete a User",
      description = "Delete a user.",
      parameters = {
          @Parameter(
              name = "id",
              description = "ID of the user to delete, Cannot be empty",
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
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * Send a verification email to the user.
   */
  @Operation(
      summary = "Verify a User",
      description = "Send a verification email to the user.",
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

  @PostMapping(value = "verify")
  public ResponseEntity<Void> verifyUser(@Valid @RequestBody VerifyUserRequest request) {
    userService.verifyUser(request.username());
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * Activate a user.
   *
   * @param token Token of the user to activate, Cannot be empty
   */
  @Operation(
      summary = "Activate a User",
      description = "Activate a user.",
      parameters = {
          @Parameter(
              name = "token",
              description = "Token of the user to activate, Cannot be empty",
              required = true)
      },
      responses = {
          @ApiResponse(
              responseCode = "204",
              description = "Successful operation"),
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
  @GetMapping(value = "activate")
  public ResponseEntity<Void> activateUser(@RequestParam String token) {
    userService.activateUser(token);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * Recovery password for the user.
   */
  @Operation(
      summary = "Recovery a User Password",
      description = "Recovery password for the user.",
      parameters = {
          @Parameter(
              name = "id",
              description = "Username of the user to recovery, Cannot be empty",
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

  @PutMapping(value = "recovery-password")
  public ResponseEntity<Void> recoveryUserPassword(
      @Valid @RequestBody RecoveryUserPasswordRequest request) {
    userService.verifyUser(request.username());
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * Set up a new password for the user.
   *
   * @param id ID of the user to update, Cannot be empty
   */
  @Operation(
      summary = "Change a User Password",
      description = "Set up a new password for the user.",
      parameters = {
          @Parameter(
              name = "id",
              description = "ID of the user to update, Cannot be empty",
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

  @PutMapping(value = "{id}/change-password")
  public ResponseEntity<Void> changeUserPassword(
      @PathVariable UUID id, @Valid @RequestBody ResetUserPasswordRequest request) {
    userService.changeUserPassword(id, request.password());
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
