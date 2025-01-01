package com.danielg.pulsar_man.infrastructure.pulsar.factory;

import com.danielg.pulsar_man.domain.model.PulsarClientProvider;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.PulsarServiceUrlRequest;
import lombok.Getter;
import org.apache.pulsar.client.api.AuthenticationFactory;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ClientFactory {
    private PulsarClientProvider pulsarClientProvider;

    public synchronized PulsarClientProvider initializePulsarClientProvider(PulsarServiceUrlRequest pulsarServiceUrlRequest) {
        try {
            if (this.pulsarClientProvider != null) {
                try {
                    this.pulsarClientProvider.getPulsarClient().close();
                } catch (PulsarClientException e) {
                    e.printStackTrace();
                }
            }
            PulsarClient pulsarClient = PulsarClient.builder()
                    .authentication(AuthenticationFactory.token(pulsarServiceUrlRequest.getToken()))
                    .serviceUrl(pulsarServiceUrlRequest.getServiceUrl())
                    .build();

            this.pulsarClientProvider = new PulsarClientProvider(pulsarClient);

            return this.pulsarClientProvider;
        } catch (PulsarClientException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized PulsarClientProvider getPulsarClientProvider() {
        return this.pulsarClientProvider;
    }

    public synchronized void setPulsarClientProviderToNull() {
        this.pulsarClientProvider = null;
    }

}
