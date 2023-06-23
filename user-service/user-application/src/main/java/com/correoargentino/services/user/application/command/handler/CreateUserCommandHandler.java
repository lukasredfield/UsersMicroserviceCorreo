package com.correoargentino.services.user.application.command.handler;

import com.correoargentino.services.user.application.command.CreateUserCommand;
import com.correoargentino.services.user.application.messaging.CommandHandler;
import com.correoargentino.services.user.application.port.output.UserRepository;
import com.correoargentino.services.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserCommandHandler implements CommandHandler<CreateUserCommand> {
    private final UserRepository userRepository;

    /**
     * Este método maneja el comando de creación de un usuario.
     *
     * @param command El comando CreateUserCommand que contiene la información necesaria para crear el usuario.
     * @return null, ya que no se devuelve ningún valor específico.
     */
    @Override
    public Void handle(CreateUserCommand command) {
// Crear una nueva instancia de User
        var user = new User();

// Establecer los atributos del usuario utilizando la información del comando
        user.create(command.id(), command.firstName(), command.lastName(), command.emailAddress(), command.phoneNumber());

// Guardar el usuario en el repositorio
        userRepository.save(user);

        return null;
    }
}
