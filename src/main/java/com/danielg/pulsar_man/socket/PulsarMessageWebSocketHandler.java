package com.danielg.pulsar_man.socket;

import com.danielg.pulsar_man.state.InMemoryPulsarConsumerState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

public class PulsarMessageWebSocketHandler extends TextWebSocketHandler {
    private InMemoryPulsarConsumerState pulsarConsumerState;
    private final List<WebSocketSession> sessions = new ArrayList<>();
    private final ObjectMapper objectMapper;

    public PulsarMessageWebSocketHandler(InMemoryPulsarConsumerState pulsarConsumerState) {
        this.pulsarConsumerState = pulsarConsumerState;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("WebSocket connection established with session: " + session.getId());
        this.startPulsarConsumer(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("WebSocket connection closed with session: " + session.getId());
    }

    // Extract the topic from the incoming WebSocket message
    private String parseTopicFromMessage(String message) {
        return message.split(":")[1].replaceAll("[\"}]", "").trim();
    }

    // Consume Pulsar messages and send them to the client via WebSocket
    public void startPulsarConsumer(WebSocketSession session) {
        new Thread(() -> {
            try {
                Consumer<?> consumer = this.pulsarConsumerState.getPulsarConsumer().getConsumer();

                while (session.isOpen()) {
                    Message<?> msg = consumer.receive();

                    // Build a map for the JSON message
                    Map<String, Object> jsonResponse = new HashMap<>();
                    jsonResponse.put("publishDate", new Date(msg.getPublishTime()));
                    jsonResponse.put("value", msg.getValue()); // Add msg value directly

                    // Convert the map to a JSON string using Jackson
                    String jsonMessage = objectMapper.writeValueAsString(jsonResponse);

                    // Send the JSON message to the WebSocket client
                    session.sendMessage(new TextMessage(jsonMessage));

                    // Acknowledge the message after sending
                    consumer.acknowledge(msg);
                }

                consumer.close();
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    session.sendMessage(new TextMessage("Error: " + e.getMessage()));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }).start();
    }


}
