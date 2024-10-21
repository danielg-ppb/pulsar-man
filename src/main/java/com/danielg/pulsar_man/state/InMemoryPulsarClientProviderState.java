package com.danielg.pulsar_man.state;

import com.danielg.pulsar_man.model.PulsarClientProvider;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.stereotype.Component;

@Getter
@Component
public class InMemoryPulsarClientProviderState {
    private PulsarClientProvider pulsarClientProvider;

    public synchronized void initializePulsarClientProvider(String serviceUrl) {
        if (this.pulsarClientProvider != null) {
            try {
                this.pulsarClientProvider.getPulsarClient().close();
            } catch (PulsarClientException e) {
                e.printStackTrace();
            }
        }
        this.pulsarClientProvider = new PulsarClientProvider(serviceUrl);
    }

    public synchronized PulsarClientProvider getPulsarClientProvider() {
        return this.pulsarClientProvider;
    }

    public synchronized void closePulsarClientProvider() {
        if (this.pulsarClientProvider != null) {
            try {
                this.pulsarClientProvider.getPulsarClient().close();
            } catch (PulsarClientException e) {
                e.printStackTrace();
            } finally {
                this.pulsarClientProvider = null;
            }
        }
    }

    @PreDestroy
    public void shutdown() {
        closePulsarClientProvider(); // Close the consumer during shutdown
    }

}