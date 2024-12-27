package com.danielg.pulsar_man.domain.model;

import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.PulsarServiceUrlRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.pulsar.client.api.AuthenticationFactory;
import org.apache.pulsar.client.api.PulsarClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
public class PulsarClientProvider {
    private static final Logger logger = LoggerFactory.getLogger(PulsarClientProvider.class);

    private PulsarClient pulsarClient;

    public PulsarClientProvider(PulsarServiceUrlRequest pulsarServiceUrlRequest) {
        try {
            this.pulsarClient = PulsarClient.builder()
                    .authentication(AuthenticationFactory.token(pulsarServiceUrlRequest.getToken()))
                    .serviceUrl(pulsarServiceUrlRequest.getServiceUrl())
                    .build();
        } catch (Exception e) {
            logger.error("Failed to initialize pulsar client", e);
        }
    }
}
