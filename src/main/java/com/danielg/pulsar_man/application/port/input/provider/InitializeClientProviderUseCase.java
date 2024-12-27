package com.danielg.pulsar_man.application.port.input.provider;

import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.PulsarServiceUrlRequest;
import org.apache.pulsar.client.api.PulsarClientException;

public interface InitializeClientProviderUseCase {
    void initializeWithServiceUrl(PulsarServiceUrlRequest pulsarServiceUrlRequest) throws PulsarClientException;
}
