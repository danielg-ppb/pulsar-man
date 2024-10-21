package com.danielg.pulsar_man.service;

import com.danielg.pulsar_man.dto.PulsarConsumerDto;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;

import java.util.List;

public interface PulsarConsumerService {
    public List<String> consumeMessages(Integer messageCount) throws PulsarClientException;
    public void initializeConsumer(String topicName, String subscriptionName, String schemaType, String initialPosition) throws PulsarClientException;
    public void initializeConsumer(PulsarConsumerDto pulsarConsumerDto) throws PulsarClientException;
    public Consumer<?> getPulsarConsumerInstance();
}
