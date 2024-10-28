package com.danielg.pulsar_man.config;

import com.danielg.pulsar_man.socket.PulsarMessageWebSocketHandler;
import com.danielg.pulsar_man.state.PulsarConsumerManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
        private final PulsarConsumerManager pulsarConsumerState;

    public WebSocketConfig(PulsarConsumerManager pulsarConsumerState) {
        this.pulsarConsumerState = pulsarConsumerState;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new PulsarMessageWebSocketHandler(this.pulsarConsumerState), "/pulsar/messages")
                .setAllowedOrigins("*"); // Allow requests from all origins
    }

    @Bean
    public PulsarMessageWebSocketHandler pulsarMessageWebSocketHandler() {
        return new PulsarMessageWebSocketHandler(pulsarConsumerState);
    }
}
