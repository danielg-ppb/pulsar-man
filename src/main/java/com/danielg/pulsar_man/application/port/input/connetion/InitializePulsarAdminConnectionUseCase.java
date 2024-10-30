package com.danielg.pulsar_man.application.port.input.connetion;

import org.apache.pulsar.client.api.PulsarClientException;

public interface InitializePulsarAdminConnectionUseCase {
    void initializePulsarAdmin(String serviceUrl) throws PulsarClientException;
}
