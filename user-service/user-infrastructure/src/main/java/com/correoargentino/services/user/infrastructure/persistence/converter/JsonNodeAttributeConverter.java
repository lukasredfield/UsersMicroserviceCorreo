package com.correoargentino.services.user.infrastructure.persistence.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.hibernate.HibernateException;

import java.io.IOException;

/**
 * Convertidor de atributos que convierte entre un objeto JsonNode y una cadena de texto (String).
 * <p>
 * Esta clase utiliza la anotación @Converter de JPA para indicar que es un convertidor de atributos.
 * Implementa la interfaz AttributeConverter<JsonNode, String> de JPA y define los métodos convertToDatabaseColumn() y convertToEntityAttribute() para realizar la conversión.
 * Utiliza un objeto ObjectMapper de la biblioteca Jackson para realizar la conversión entre JsonNode y String.
 */
@Converter
public class JsonNodeAttributeConverter implements AttributeConverter<JsonNode, String> {
  private static final ObjectMapper mapper = new ObjectMapper();

  /**
   * Método que convierte un objeto JsonNode a una cadena de texto (String).
   *
   * @param jsonNode El objeto JsonNode a convertir.
   * @return La cadena de texto resultante, o null si el JsonNode es null.
   * @throws HibernateException Si ocurre un error durante la conversión.
   */
  @Override
  public String convertToDatabaseColumn(JsonNode jsonNode) {
    if (jsonNode == null) {
      return null;
    }
    try {
      return mapper.writeValueAsString(jsonNode);
    } catch (JsonProcessingException e) {
      throw new HibernateException("Failed to convert JsonNode to String", e);
    }
  }

  /**
   * Método que convierte una cadena de texto (String) a un objeto JsonNode.
   *
   * @param databaseValue La cadena de texto a convertir.
   * @return El objeto JsonNode resultante, o null si la cadena de texto es null.
   * @throws HibernateException Si ocurre un error durante la conversión.
   */
  @Override
  public JsonNode convertToEntityAttribute(String databaseValue) {
    if (databaseValue == null) {
      return null;
    }
    try {
      return mapper.readValue(databaseValue, JsonNode.class);
    } catch (IOException e) {
      throw new HibernateException("Failed to convert String to JsonNode", e);
    }
  }
}
