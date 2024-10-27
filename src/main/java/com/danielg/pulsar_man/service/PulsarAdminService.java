package com.danielg.pulsar_man.service;

import com.danielg.pulsar_man.dto.TopicListDto;
import org.apache.pulsar.client.api.PulsarClientException;

import java.util.List;

public interface PulsarAdminService {
    public void initializePulsarAdmin(String serviceUrl) throws PulsarClientException;
    public TopicListDto listTopics(String tenant, String namespace);
}
