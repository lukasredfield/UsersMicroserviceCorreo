package com.correoargentino.services.user.presentation;

import com.correoargentino.services.user.application.command.DeleteUserCommand;
import com.correoargentino.services.user.application.port.input.UserService;
import com.correoargentino.services.user.application.query.GetUserQuery;
import com.correoargentino.services.user.presentation.mapper.CommandMapper;
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
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The mechanism that dispatches Command objects to their appropriate CommandHandler.
 * CommandHandlers can subscribe and unsubscribe to specific commands identified by their on the
 * command bus. Only a single handler may be subscribed for a single command name at any time.
 *
 * @author Claudio Amoedo
 * @since 0.1
 */
@Slf4j
@RestController
@RequestMapping(value = "v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  private final CommandMapper commandMapper;

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
      @RequestBody CreateUserRequest request) {
    log.info("Creating a new user");
    var id = userService.createUser(commandMapper.fromRequest(request));
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
    var user = userService.getUser(new GetUserQuery(id));
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
    userService.updateUser(commandMapper.fromRequest(id, request));
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
    userService.deleteUser(new DeleteUserCommand(id));
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
