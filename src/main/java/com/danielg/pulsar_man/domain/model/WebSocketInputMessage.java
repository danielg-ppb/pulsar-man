package com.danielg.pulsar_man.domain.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.text.StringEscapeUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class WebSocketInputMessage {
    private LocalDateTime publishTime;
    private Object message;

    public WebSocketInputMessage(LocalDateTime publishTime, Object message) {
        this.publishTime = publishTime;
        this.message = message;
    }

    public String toJson(ObjectMapper objectMapper) throws JsonProcessingException {
        Map<String, Object> jsonResponse = new HashMap<>();

        jsonResponse.put("publishDate", this.publishTime.toString());
        jsonResponse.put("value", decodeMessage(this.message, objectMapper));

        // Convert the map to a JSON string using Jackson
        return objectMapper.writeValueAsString(jsonResponse);
    }

    private Object decodeMessage(Object message, ObjectMapper objectMapper) {
        if (message instanceof String) {
            String rawMessage = (String) message;

            try {
                // Attempt to parse as JSON and return parsed object
                return objectMapper.readValue(rawMessage, Object.class);
            } catch (JsonProcessingException e) {
                // If not JSON, decode Unicode escape sequences
                return StringEscapeUtils.unescapeJson(rawMessage);
            }
        }

        // Return the original message if it's not a String
        return message;
    }
}
