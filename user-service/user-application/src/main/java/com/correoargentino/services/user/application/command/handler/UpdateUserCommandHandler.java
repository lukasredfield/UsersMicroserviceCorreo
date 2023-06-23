package com.correoargentino.services.user.application.command.handler;

import com.correoargentino.services.user.application.command.UpdateUserCommand;
import com.correoargentino.services.user.application.exception.UserNotFoundException;
import com.correoargentino.services.user.application.messaging.CommandHandler;
import com.correoargentino.services.user.application.port.output.UserRepository;
import com.correoargentino.services.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserCommandHandler implements CommandHandler<UpdateUserCommand> {
    private final UserRepository userRepository;

    /**
     * Este método maneja el comando de actualización de un usuario.
     *
     * @param command El comando UpdateUserCommand que contiene la información necesaria para actualizar el usuario.
     * @return null, ya que no se devuelve ningún valor específico.
     * @throws UserNotFoundException si no se encuentra el usuario con el ID especificado.
     */
    @Override
    public Void handle(UpdateUserCommand command) throws UserNotFoundException {
// Buscar el usuario en el repositorio de usuarios por su ID
        User user = userRepository.findById(command.id())
                .orElseThrow(() -> new UserNotFoundException(command.id()));

// Actualizar los atributos del usuario utilizando la información del comando
        user.update(command.firstName(),
                command.lastName(), command.emailAddress(), command.phoneNumber());

// Guardar el usuario actualizado en el repositorio
        userRepository.save(user);

        return null;
    }
}
