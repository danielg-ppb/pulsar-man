package com.danielg.pulsar_man.socket;

import com.danielg.pulsar_man.service.PulsarConsumerService;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClient;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PulsarMessageWebSocketHandler extends TextWebSocketHandler {
    private PulsarConsumerService pulsarConsumerService;
    private final List<WebSocketSession> sessions = new ArrayList<>();

    public PulsarMessageWebSocketHandler(PulsarConsumerService pulsarConsumerService) {
        this.pulsarConsumerService = pulsarConsumerService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("WebSocket connection established with session: " + session.getId());
        this.startPulsarConsumer(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
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
                Consumer<?> consumer = this.pulsarConsumerService.getPulsarConsumerInstance();

                while (session.isOpen()) {
                    Message<?> msg = consumer.receive();
                    session.sendMessage(new TextMessage("Message from Pulsar: " + msg.getValue()));
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
