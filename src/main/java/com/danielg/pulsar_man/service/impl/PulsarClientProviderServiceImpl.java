package com.danielg.pulsar_man.service.impl;

import com.danielg.pulsar_man.service.PulsarAdminService;
import com.danielg.pulsar_man.service.PulsarClientProviderService;
import com.danielg.pulsar_man.state.InMemoryPulsarAdminState;
import com.danielg.pulsar_man.state.InMemoryPulsarClientProviderState;
import jakarta.annotation.PreDestroy;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.stereotype.Service;

@Service
public class PulsarClientProviderServiceImpl implements PulsarClientProviderService {
    private final InMemoryPulsarClientProviderState pulsarClientProviderState;

    public PulsarClientProviderServiceImpl(InMemoryPulsarClientProviderState pulsarClientProviderState) {
        this.pulsarClientProviderState = pulsarClientProviderState;
    }

    @Override
    public void initializeWithServiceUrl(String serviceUrl) throws PulsarClientException {
        this.pulsarClientProviderState.initializePulsarClientProvider(serviceUrl);
    }

    public synchronized void closePulsarClientProvider() {
        if (this.pulsarClientProviderState != null) {
            try {
                this.pulsarClientProviderState.getPulsarClientProvider().getPulsarClient().close();
            } catch (PulsarClientException e) {
                e.printStackTrace();
            } finally {
                this.pulsarClientProviderState.setPulsarClientProviderToNull();
            }
        }
    }

    @PreDestroy
    public void shutdown() {
        closePulsarClientProvider(); // Close the consumer during shutdown
    }
}
