package com.correoargentino.services.user.application.command.handler;

import com.correoargentino.services.user.application.command.CreateUserCommand;
import com.correoargentino.services.user.application.messaging.CommandHandler;
import com.correoargentino.services.user.application.port.output.UserRepository;
import com.correoargentino.services.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Clase que maneja el comando de creación de usuario.
 *
 * Esta clase implementa la interfaz CommandHandler<CreateUserCommand> para indicar que es responsable de manejar el comando de creación de usuario.
 * Utiliza la anotación @Component de Spring para indicar que es un componente que se puede inyectar en otras clases.
 * También utiliza la anotación @RequiredArgsConstructor de Lombok para generar un constructor con argumentos para los campos marcados con 'final'.
 */
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
        var user = new User();
        user.create(command.id(), command.firstName(), command.lastName(), command.emailAddress(), command.phoneNumber());
        userRepository.save(user);

        return null;
    }
}
