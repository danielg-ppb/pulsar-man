package com.danielg.pulsar_man.domain.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

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
        jsonResponse.put("value", this.message);

        // Convert the map to a JSON string using Jackson
        return objectMapper.writeValueAsString(jsonResponse);
    }
}
