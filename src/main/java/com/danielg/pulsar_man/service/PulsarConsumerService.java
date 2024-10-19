package com.danielg.pulsar_man.service;

import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;

import java.util.List;

public interface PulsarConsumerService<T> {
    public List<?> consumeMessages(String topicName, Integer messageCount, Schema<T> schemaType) throws PulsarClientException;
}
