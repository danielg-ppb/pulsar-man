package com.danielg.pulsar_man.state;

import com.danielg.pulsar_man.model.PulsarClientProvider;
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

    public synchronized void setPulsarClientProviderToNull(){
        this.pulsarClientProvider = null;
    }

}
