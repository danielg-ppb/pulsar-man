package com.danielg.pulsar_man.infrastructure.adapter.output.websocket.handler;

import com.danielg.pulsar_man.domain.model.WebSocketInputMessage;
import com.danielg.pulsar_man.infrastructure.pulsar.factory.ConsumerFactory;
import com.danielg.pulsar_man.utils.ProtoUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;
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
import java.util.concurrent.ConcurrentHashMap;

public class PulsarMessageWebSocketHandler extends TextWebSocketHandler {
    private ConsumerFactory consumerFactory;
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final Map<String, Consumer<?>> consumers = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;

    public PulsarMessageWebSocketHandler(ConsumerFactory consumerFactory) {
        this.consumerFactory = consumerFactory;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.put(session.getId(), session);
        System.out.println("WebSocket connection established with session: " + session.getId());
        this.startPulsarConsumer(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session.getId());
        // Close the consumer for the session
        Consumer<?> consumer = consumers.remove(session.getId());
        if (consumer != null) {
            try {
                consumer.close();
                System.out.println("Closed Pulsar consumer for session: " + session.getId());
            } catch (IOException e) {
                System.err.println("Failed to close Pulsar consumer: " + e.getMessage());
            }
        }
        System.out.println("WebSocket connection closed with session: " + session.getId());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        System.err.println("Transport error in session: " + session.getId() + " - " + exception.getMessage());
        sessions.remove(session.getId());
        try {
            session.close(CloseStatus.SERVER_ERROR);
        } catch (IOException e) {
            System.err.println("Error closing session: " + e.getMessage());
        }
    }

    // Consume Pulsar messages and send them to the client via WebSocket
    public void startPulsarConsumer(WebSocketSession session) {
        new Thread(() -> {
            Consumer<?> consumer = null;
            try {
                // Initialize a new Pulsar consumer
                consumer = consumerFactory.getPulsarConsumer().getConsumer();
                consumers.put(session.getId(), consumer);

                while (session.isOpen()) {
                    consumeDynamicMessages(session, consumer);
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    sendErrorMessage(session, e);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } finally {
                // Cleanup when thread ends
                if (consumer != null) {
                    consumers.remove(session.getId());
                    try {
                        consumer.close();
                        System.out.println("Stopped Pulsar consumer for session: " + session.getId());
                    } catch (IOException e) {
                        System.err.println("Failed to close Pulsar consumer: " + e.getMessage());
                    }
                }
            }
        }).start();
    }

    private static void sendErrorMessage(WebSocketSession session, Exception e) throws IOException {
        if (session.isOpen()) {
            session.sendMessage(new TextMessage("Error: " + e.getMessage()));
        }
    }

    private void consumeDynamicMessages(WebSocketSession session, Consumer<?> consumer) throws Exception {
        Message<?> msg = consumer.receive();
        String msgValue;

        String schemaFile = consumerFactory.getPulsarConsumer().getSchemaFile();
        if (schemaFile != null) {
            Descriptors.Descriptor descriptor = ProtoUtils.loadProtoDescriptor("uploads/" + schemaFile);
            DynamicMessage dynamicMessage = DynamicMessage.parseFrom(descriptor, (byte[]) msg.getData());
            msgValue = ProtoUtils.convertDynamicMessageToJson(objectMapper, dynamicMessage);
        } else {
            msgValue = new String(msg.getData());
        }

        WebSocketInputMessage webSocketInputMessage = new WebSocketInputMessage(
                LocalDateTime.ofEpochSecond(msg.getPublishTime() / 1000, 0, ZoneOffset.UTC), msgValue);

        String jsonMessage = webSocketInputMessage.toJson(objectMapper);

        if (session.isOpen()) {
            session.sendMessage(new TextMessage(jsonMessage));
            consumer.acknowledge(msg);
        }
    }

}
