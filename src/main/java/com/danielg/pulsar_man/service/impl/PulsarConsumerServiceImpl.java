package com.danielg.pulsar_man.service.impl;

import com.danielg.pulsar_man.config.PulsarClientProvider;
import com.danielg.pulsar_man.service.PulsarConsumerService;
import com.danielg.pulsar_man.utils.SchemaProvider;
import org.apache.pulsar.client.api.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PulsarConsumerServiceImpl implements PulsarConsumerService {
    private final PulsarClientProvider pulsarClientProvider;

    public PulsarConsumerServiceImpl(PulsarClientProvider pulsarClientProvider) {
        this.pulsarClientProvider = pulsarClientProvider;
    }

    @Override
    public List<String> consumeMessages(String topicName, Integer messageCount, String schemaType) throws PulsarClientException {
        List<String> messages = new ArrayList<>();

        Schema<?> schemaProvider = SchemaProvider.getSchema(schemaType);
        PulsarClient pulsarClient = pulsarClientProvider.getPulsarClient();
        Consumer<?> consumer = pulsarClient.newConsumer(schemaProvider)
                .topic(topicName)
                .subscriptionName("my-subscription2")
                .subscribe();

        for (int i = 0; i < messageCount; i++) {
            Message<?> msg = consumer.receive(1, TimeUnit.SECONDS);
            if (msg == null) {
                break; // No more messages to consume
            }
            messages.add(msg.getValue().toString());
            consumer.acknowledge(msg);
        }

        consumer.close();
        return messages;
    }
}
