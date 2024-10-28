package com.danielg.pulsar_man.service.admin;

import org.apache.pulsar.client.api.PulsarClientException;

public interface PulsarAdminConnectionService {
    public void initializePulsarAdmin(String serviceUrl) throws PulsarClientException;
}
