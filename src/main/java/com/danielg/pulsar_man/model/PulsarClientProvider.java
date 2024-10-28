package com.danielg.pulsar_man.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.pulsar.client.api.PulsarClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
public class PulsarClientProvider {
    private static final Logger logger = LoggerFactory.getLogger(PulsarClientProvider.class);

    private PulsarClient pulsarClient;

    public PulsarClientProvider(String serviceUrl) {
        try {
            this.pulsarClient = PulsarClient.builder()
                    .serviceUrl(serviceUrl)
                    .build();
        } catch (Exception e) {
            logger.error("Failed to initialize pulsar client", e);
        }
    }
}
