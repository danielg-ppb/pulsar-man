package com.danielg.pulsar_man.service.client.impl;

import com.danielg.pulsar_man.service.client.PulsarClientProviderService;
import com.danielg.pulsar_man.state.PulsarClientManager;
import jakarta.annotation.PreDestroy;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PulsarClientProviderServiceImpl implements PulsarClientProviderService {
    private static final Logger logger = LoggerFactory.getLogger(PulsarClientProviderServiceImpl.class);

    private final PulsarClientManager pulsarClientManagerState;

    public PulsarClientProviderServiceImpl(PulsarClientManager pulsarClientManagerState) {
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
