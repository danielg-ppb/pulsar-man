package com.danielg.pulsar_man.infrastructure.adapter.output.websocket.handler;

import com.danielg.pulsar_man.domain.model.WebSocketInputMessage;
import com.danielg.pulsar_man.state.PulsarConsumerManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

public class PulsarMessageWebSocketHandler extends TextWebSocketHandler {
    private PulsarConsumerManager pulsarConsumerState;
    private final List<WebSocketSession> sessions = new ArrayList<>();
    private final ObjectMapper objectMapper;

    public PulsarMessageWebSocketHandler(PulsarConsumerManager pulsarConsumerState) {
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

                    WebSocketInputMessage webSocketInputMessage = new WebSocketInputMessage(LocalDateTime.ofEpochSecond(msg.getPublishTime() / 1000, 0, ZoneOffset.UTC), msg.getValue());
                    String jsonMessage = webSocketInputMessage.toJson(objectMapper);

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
