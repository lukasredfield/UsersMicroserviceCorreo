package com.correoargentino.services.user.infrastructure.persistence;

import com.correoargentino.services.user.application.port.output.UserReadRepository;
import com.correoargentino.services.user.application.query.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Implementación de UserReadRepository que interactúa con la base de datos para recuperar los datos de un usuario.
 * <p>
 * Esta clase utiliza la anotación @Service de Spring para indicar que es un servicio.
 * Utiliza la anotación @RequiredArgsConstructor de Lombok para generar un constructor con argumentos para los campos marcados con 'final'.
 * Implementa el método findById() de UserReadRepository para buscar un usuario por su ID en la base de datos.
 * Utiliza un objeto JdbcTemplate para ejecutar consultas SQL y un objeto ObjectMapper para convertir datos JSON a JsonNode.
 */
@Service
@RequiredArgsConstructor
public class UserReadRepositoryImpl implements UserReadRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    /**
     * Método que busca un usuario por su ID en la base de datos.
     *
     * @param id El ID del usuario a buscar.
     * @return Un Optional que contiene el usuario encontrado, o un Optional vacío si no se encontró ningún usuario con el ID especificado.
     */
    @Override
    public Optional<User> findById(UUID id) {
        var sql = """
                SELECT * FROM users s WHERE s.id = ? AND s.deleted_at IS NULL
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new User(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("username"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email_address"),
                        rs.getString("phone_number"),
                        convertToJsonNode(rs.getString("preferences")),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                ), id).stream().findFirst();
    }


    /**
     * Método privado que convierte una cadena JSON en un objeto JsonNode.
     *
     * @param json La cadena JSON a convertir.
     * @return El objeto JsonNode resultante, o null si la cadena JSON es nula.
     * @throws RuntimeException Si se produce un error durante la conversión JSON.
     */
    private JsonNode convertToJsonNode(String json) {
        if (json == null) {
            return null;
        }

        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}






