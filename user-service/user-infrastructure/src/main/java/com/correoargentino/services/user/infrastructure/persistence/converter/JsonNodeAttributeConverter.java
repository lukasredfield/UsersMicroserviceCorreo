package com.correoargentino.services.user.infrastructure.persistence.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.io.IOException;
import org.hibernate.HibernateException;

/**
 * Converter that knows how to convert between a {@link String} and {@link JsonNode}.
 *
 * @author Claudio Amoedo
 */
@Converter
public class JsonNodeAttributeConverter implements AttributeConverter<JsonNode, String> {
  private static final ObjectMapper mapper = new ObjectMapper();

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
