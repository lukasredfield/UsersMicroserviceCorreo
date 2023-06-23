package com.correoargentino.services.user.application.command.handler;

import com.correoargentino.services.user.application.command.DeleteUserCommand;
import com.correoargentino.services.user.application.exception.UserNotFoundException;
import com.correoargentino.services.user.application.messaging.CommandHandler;
import com.correoargentino.services.user.application.port.output.UserRepository;
import com.correoargentino.services.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserCommandHandler implements CommandHandler<DeleteUserCommand> {
    private final UserRepository userRepository;

    /**
     * Este método maneja el comando de eliminar un usuario.
     *
     * @param command El comando DeleteUserCommand que contiene la información necesaria para eliminar el usuario.
     * @return null, ya que no se devuelve ningún valor específico.
     * @throws UserNotFoundException si no se encuentra el usuario con el ID especificado.
     */
    @Override
    public Void handle(DeleteUserCommand command) throws UserNotFoundException {
// Buscar el usuario en el repositorio de usuarios por su ID
        User user = userRepository.findById(command.id())
                .orElseThrow(() -> new UserNotFoundException(command.id()));

// Eliminar el usuario del repositorio
        userRepository.delete(user);

        return null;
    }
}