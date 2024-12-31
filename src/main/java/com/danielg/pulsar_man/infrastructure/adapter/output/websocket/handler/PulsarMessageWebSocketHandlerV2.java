package com.danielg.pulsar_man.infrastructure.adapter.output.websocket.handler;

import com.danielg.pulsar_man.application.service.dynamic.DynamicConsumerService;
import com.danielg.pulsar_man.infrastructure.protoc.ProtocExecutor;
import com.danielg.pulsar_man.utils.DateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.util.JsonFormat;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class PulsarMessageWebSocketHandlerV2 extends TextWebSocketHandler {
    private DynamicConsumerService dynamicConsumerService;
    private ProtocExecutor protocExecutor;
    private ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public PulsarMessageWebSocketHandlerV2(DynamicConsumerService dynamicConsumerService, ProtocExecutor protocExecutor) {
        this.dynamicConsumerService = dynamicConsumerService;
        this.protocExecutor = protocExecutor;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException, ClassNotFoundException {
        this.clearConnections();
        sessions.put(session.getId(), session);
        System.out.println("Session ID" + session.getId());
        System.out.println("WebSocket connection established with session: " + session.getId());
        //startHeartbeat(session);
        this.consumeMessages(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        this.clearConnections();
        System.out.println("WebSocket connection closed with session: " + session.getId());
    }


    private void consumeMessages(WebSocketSession session) throws IOException, ClassNotFoundException {
        System.out.println("Session" + session);
        String outerClassName = this.dynamicConsumerService.getDynamicConsumerSingleton().getOuterClassName();
        String mainInnerClassName = this.dynamicConsumerService.getDynamicConsumerSingleton().getMainInnerClassName();
        System.out.println("Dynamic stuff" + this.dynamicConsumerService.getDynamicConsumerSingleton());

        Class<? extends GeneratedMessageV3> protobufClass = this.protocExecutor
                .getClassFromProtoSchema(outerClassName, mainInnerClassName);
        try {
            Consumer<? extends GeneratedMessageV3> consumer =
                    (Consumer<? extends GeneratedMessageV3>) this.dynamicConsumerService.startConsumer(protobufClass);
            while (session.isOpen() && sessions.containsKey(session.getId())) {
                Message<? extends GeneratedMessageV3> consumedMessage = consumer.receive(100, TimeUnit.MILLISECONDS);
                if (consumedMessage != null) {
                    consumeDynamicMessages(session, consumer, consumedMessage);
                }
            }
            System.out.println("Session closed");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void consumeDynamicMessages(WebSocketSession session, Consumer<?> consumer, Message<? extends GeneratedMessageV3> msg) {
        try {
            if (session.isOpen()) {
                String protoJson = JsonFormat.printer().includingDefaultValueFields().print(msg.getValue());
                ObjectMapper objectMapper = new ObjectMapper();

                String publishDate = DateUtils.convertLongToDateTimeString(msg.getPublishTime());

                String jsonMessage = objectMapper.createObjectNode()
                        .put("publishDate", publishDate)
                        .putPOJO("message", objectMapper.readTree(protoJson))
                        .toString();
                System.out.println("Sending message to session: " + session.getId());
                System.out.println("Message: " + jsonMessage);
                session.sendMessage(new TextMessage(jsonMessage));
                consumer.acknowledge(msg);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearConnections() {
        for (Map.Entry<String, WebSocketSession> entry : sessions.entrySet()) {
            WebSocketSession session = entry.getValue();
            System.out.println("Closing session: " + entry.getKey());
            try {
                if (session.isOpen()) {
                    session.close(CloseStatus.NORMAL); // Close the session gracefully
                }
            } catch (IOException e) {
                System.err.println("Error closing session: " + e.getMessage());
            } finally {
                sessions.remove(entry.getKey()); // Remove from map after attempting to close
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        System.err.println("Transport error in session: " + session.getId() + " - " + exception.getMessage());
        this.clearConnections();
        try {
            session.close(CloseStatus.SERVER_ERROR);
        } catch (IOException e) {
            System.err.println("Error closing session: " + e.getMessage());
        }
    }

}
