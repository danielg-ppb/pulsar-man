package com.danielg.pulsar_man.application.port.input.provider;

import org.apache.pulsar.client.api.PulsarClientException;

public interface InitializeClientProviderUseCase {
    void initializeWithServiceUrl(String serviceUrl) throws PulsarClientException;
}
