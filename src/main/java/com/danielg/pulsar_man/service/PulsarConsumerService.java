package com.danielg.pulsar_man.service;

import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;

import java.util.List;

public interface PulsarConsumerService {
    public List<String> consumeMessages(String topicName, Integer messageCount, String schemaType) throws PulsarClientException;
}
