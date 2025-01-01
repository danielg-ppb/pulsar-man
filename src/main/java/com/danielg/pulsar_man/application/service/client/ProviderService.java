package com.danielg.pulsar_man.application.service.client;

import com.danielg.pulsar_man.application.port.input.provider.InitializeClientProviderUseCase;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.PulsarServiceUrlRequest;
import com.danielg.pulsar_man.infrastructure.pulsar.factory.ClientFactory;
import jakarta.annotation.PreDestroy;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProviderService implements InitializeClientProviderUseCase {
    private static final Logger logger = LoggerFactory.getLogger(ProviderService.class);

    private final ClientFactory clientFactory;

    public ProviderService(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public void initializeWithServiceUrl(PulsarServiceUrlRequest pulsarServiceUrlRequest) {
        this.clientFactory.initializePulsarClientProvider(pulsarServiceUrlRequest);
    }

    public synchronized void closePulsarClientProvider() {
        if (this.clientFactory != null) {
            try {
                this.clientFactory.getPulsarClientProvider().getPulsarClient().close();
            } catch (PulsarClientException e) {
                logger.error("Cannot close pulsar client provider", e);
            } finally {
                this.clientFactory.setPulsarClientProviderToNull();
            }
        }
    }

    @PreDestroy
    public void shutdown() {
        closePulsarClientProvider(); // Close the consumer during shutdown
    }
}
