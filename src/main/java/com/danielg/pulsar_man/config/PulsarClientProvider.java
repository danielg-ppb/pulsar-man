package com.danielg.pulsar_man.config;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

public interface PulsarClientProvider {
    void setServiceUrl(String serviceUrl) throws PulsarClientException;

    PulsarClient getPulsarClient();

    void close() throws PulsarClientException;
}
