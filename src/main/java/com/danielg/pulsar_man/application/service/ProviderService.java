package com.danielg.pulsar_man.application.service;

import com.danielg.pulsar_man.application.port.input.provider.InitializeClientProviderUseCase;
import com.danielg.pulsar_man.infrastructure.pulsar.factory.PulsarClientFactory;
import jakarta.annotation.PreDestroy;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProviderService implements InitializeClientProviderUseCase {
    private static final Logger logger = LoggerFactory.getLogger(ProviderService.class);

    private final PulsarClientFactory pulsarClientManagerState;

    public ProviderService(PulsarClientFactory pulsarClientManagerState) {
        this.pulsarClientManagerState = pulsarClientManagerState;
    }

    @Override
    public void initializeWithServiceUrl(String serviceUrl) throws PulsarClientException {
        this.pulsarClientManagerState.initializePulsarClientProvider(serviceUrl);
    }

    public synchronized void closePulsarClientProvider() {
        if (this.pulsarClientManagerState != null) {
            try {
                this.pulsarClientManagerState.getPulsarClientProvider().getPulsarClient().close();
            } catch (PulsarClientException e) {
                logger.error("Cannot close pulsar client provider", e);
            } finally {
                this.pulsarClientManagerState.setPulsarClientProviderToNull();
            }
        }
    }

    @PreDestroy
    public void shutdown() {
        closePulsarClientProvider(); // Close the consumer during shutdown
    }
}
