package com.danielg.pulsar_man.infrastructure.adapter.output.websocket.config;

import com.danielg.pulsar_man.application.service.dynamic.DynamicConsumerService;
import com.danielg.pulsar_man.domain.repository.DynamicConsumerRepository;
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
    private final DynamicConsumerRepository dynamicConsumerRepository;

    public WebSocketConfig(DynamicConsumerService dynamicConsumerService,
                           ProtocExecutor protocExecutor,
                           DynamicConsumerRepository dynamicConsumerRepository) {
        this.dynamicConsumerService = dynamicConsumerService;
        this.protocExecutor = protocExecutor;
        this.dynamicConsumerRepository = dynamicConsumerRepository;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new PulsarMessageWebSocketHandlerV2(this.dynamicConsumerService,
                                this.protocExecutor, this.dynamicConsumerRepository)
                        , "/pulsar/messages")
                .setAllowedOrigins("*");
    }


    @Bean
    public PulsarMessageWebSocketHandlerV2 pulsarMessageWebSocketHandlerV2() {
        return new PulsarMessageWebSocketHandlerV2(dynamicConsumerService, this.protocExecutor, this.dynamicConsumerRepository);
    }
}
