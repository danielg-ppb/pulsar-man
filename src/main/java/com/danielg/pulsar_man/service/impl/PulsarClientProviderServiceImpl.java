package com.danielg.pulsar_man.service.impl;

import com.danielg.pulsar_man.service.PulsarClientProviderService;
import com.danielg.pulsar_man.state.InMemoryPulsarClientProviderState;
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
}
