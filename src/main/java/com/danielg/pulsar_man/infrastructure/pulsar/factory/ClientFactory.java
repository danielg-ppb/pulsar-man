package com.danielg.pulsar_man.infrastructure.pulsar.factory;

import com.danielg.pulsar_man.domain.model.PulsarClientProvider;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.PulsarServiceUrlRequest;
import lombok.Getter;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ClientFactory {
    private PulsarClientProvider pulsarClientProvider;

    public synchronized void initializePulsarClientProvider(PulsarServiceUrlRequest pulsarServiceUrlRequest) {
        if (this.pulsarClientProvider != null) {
            try {
                this.pulsarClientProvider.getPulsarClient().close();
            } catch (PulsarClientException e) {
                e.printStackTrace();
            }
        }
        this.pulsarClientProvider = new PulsarClientProvider(pulsarServiceUrlRequest);
    }

    public synchronized PulsarClientProvider getPulsarClientProvider() {
        return this.pulsarClientProvider;
    }

    public synchronized void setPulsarClientProviderToNull() {
        this.pulsarClientProvider = null;
    }

}
