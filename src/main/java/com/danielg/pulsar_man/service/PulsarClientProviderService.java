package com.danielg.pulsar_man.service;

import org.apache.pulsar.client.api.PulsarClientException;

public interface PulsarClientProviderService {
    void initializeWithServiceUrl(String serviceUrl) throws PulsarClientException;
}
