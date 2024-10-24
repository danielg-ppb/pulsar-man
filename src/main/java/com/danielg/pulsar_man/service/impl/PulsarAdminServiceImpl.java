package com.danielg.pulsar_man.service.impl;

import com.danielg.pulsar_man.service.PulsarTopicService;
import com.danielg.pulsar_man.state.InMemoryPulsarAdminState;
import org.apache.pulsar.client.api.PulsarClientException;

import java.util.List;

public class PulsarTopicServiceImpl implements PulsarTopicService {
    private InMemoryPulsarAdminState pulsarAdminState;

    public void initializePulsarAdmin(String serviceUrl) throws PulsarClientException {
        if (pulsarAdminState != null && pulsarAdminState.getPulsarAdmin() != null) {
            pulsarAdminState.getPulsarAdmin().close();
        }

        if (pulsarAdminState != null) {
            pulsarAdminState.initializePulsarAdmin(serviceUrl);
        }
    }

    @Override
    public List<String> listTopics(String tenant, String namespace) {
        try {
            String namespacePath = tenant + "/" + namespace;
            return pulsarAdminState.getPulsarAdmin().topics().getList(namespacePath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to list topics", e);
        }
    }
}
