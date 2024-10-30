package com.danielg.pulsar_man.application.port.input.consumer;

import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.PulsarConsumerRequest;
import org.apache.pulsar.client.api.PulsarClientException;

import java.net.MalformedURLException;

public interface InitializeConsumerUseCase {
    void initializeConsumer(PulsarConsumerRequest pulsarConsumerDto) throws PulsarClientException;
}
