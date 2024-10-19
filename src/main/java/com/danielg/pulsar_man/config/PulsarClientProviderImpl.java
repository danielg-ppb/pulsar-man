package com.danielg.pulsar_man.config;

import lombok.Getter;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.shade.javax.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PulsarClientProviderImpl implements PulsarClientProvider{
    private PulsarClient pulsarClient;

    public void setServiceUrl(String serviceUrl) throws PulsarClientException {
        if (this.pulsarClient != null) {
            this.pulsarClient.close();
        }
        this.pulsarClient = PulsarClient.builder()
                .serviceUrl(serviceUrl)
                .build();
    }

    @PreDestroy
    public void close() throws PulsarClientException {
        if (pulsarClient != null) {
            pulsarClient.close();
        }
    }

}
