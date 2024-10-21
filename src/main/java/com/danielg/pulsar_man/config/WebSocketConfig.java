package com.danielg.pulsar_man.config;

import com.danielg.pulsar_man.service.PulsarConsumerService;
import com.danielg.pulsar_man.socket.PulsarMessageWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
        private final PulsarConsumerService pulsarConsumerService;

    public WebSocketConfig(PulsarConsumerService pulsarConsumerService) {
        this.pulsarConsumerService = pulsarConsumerService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new PulsarMessageWebSocketHandler(this.pulsarConsumerService), "/pulsar/messages")
                .setAllowedOrigins("*"); // Allow requests from all origins
    }

    @Bean
    public PulsarMessageWebSocketHandler pulsarMessageWebSocketHandler() {
        return new PulsarMessageWebSocketHandler(pulsarConsumerService);
    }
}
