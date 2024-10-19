package com.danielg.pulsar_man.service.impl;

import com.danielg.pulsar_man.config.PulsarClientProvider;
import com.danielg.pulsar_man.service.PulsarConsumerService;
import org.apache.pulsar.client.api.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PulsarConsumerServiceImpl<T> implements PulsarConsumerService<T> {
    private final PulsarClientProvider pulsarClientProvider;

    public PulsarConsumerServiceImpl(PulsarClientProvider pulsarClientProvider) {
        this.pulsarClientProvider = pulsarClientProvider;
    }

    @Override
    public List<T> consumeMessages(String topicName, Integer messageCount, Schema<T> schemaType) throws PulsarClientException {
        List<T> messages = new ArrayList<>();

        PulsarClient pulsarClient = pulsarClientProvider.getPulsarClient();
        Consumer<T> consumer = pulsarClient.newConsumer(schemaType)
                .topic(topicName)
                .subscriptionName("my-subscription")
                .subscribe();

        for (int i = 0; i < messageCount; i++) {
            Message<T> msg = consumer.receive();
            messages.add(msg.getValue());
            consumer.acknowledge(msg);
        }

        consumer.close();
        return messages;
    }
}
