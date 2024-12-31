package com.danielg.pulsar_man.infrastructure.adapter.output.websocket.config;

import com.danielg.pulsar_man.application.service.dynamic.DynamicConsumerService;
import com.danielg.pulsar_man.infrastructure.adapter.output.websocket.handler.PulsarMessageWebSocketHandlerV2;
import com.danielg.pulsar_man.infrastructure.protoc.ProtocExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final DynamicConsumerService dynamicConsumerService;
    private final ProtocExecutor protocExecutor;

    public WebSocketConfig(DynamicConsumerService dynamicConsumerService) {
        this.dynamicConsumerService = dynamicConsumerService;
        this.protocExecutor = new ProtocExecutor();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        /*registry.addHandler(new PulsarMessageWebSocketHandler(this.consumerFactory), "/pulsar/messages")
                .setAllowedOrigins("*"); // Allow requests from all origins*/
        registry.addHandler(new PulsarMessageWebSocketHandlerV2(this.dynamicConsumerService, this.protocExecutor)
                        , "/pulsar/messages")
                .setAllowedOrigins("*");
    }


    @Bean
    public PulsarMessageWebSocketHandlerV2 pulsarMessageWebSocketHandlerV2() {
        return new PulsarMessageWebSocketHandlerV2(dynamicConsumerService, this.protocExecutor);
    }
}
