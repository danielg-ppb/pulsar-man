package com.danielg.pulsar_man.service.impl;

import com.danielg.pulsar_man.config.PulsarClientProvider;
import com.danielg.pulsar_man.config.PulsarConsumer;
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
    private Consumer<?> pulsarConsumer;

    public PulsarConsumerServiceImpl(PulsarClientProvider pulsarClientProvider) {
        this.pulsarClientProvider = pulsarClientProvider;
    }

    public void initializeConsumer(String topicName, String subscriptionName, String schemaType) throws PulsarClientException {
        this.pulsarConsumer = new PulsarConsumer(pulsarClientProvider, topicName, subscriptionName, schemaType).initializeConsumer();
    }

    @Override
    public List<String> consumeMessages(Integer messageCount) throws PulsarClientException {
        List<String> messages = new ArrayList<>();

        for (int i = 0; i < messageCount; i++) {
            Message<?> msg = pulsarConsumer.receive(1, TimeUnit.SECONDS);
            if (msg == null) {
                break; // No more messages to consume
            }
            messages.add(msg.getValue().toString());
            pulsarConsumer.acknowledge(msg);
        }

        //pulsarConsumer.close();
        return messages;
    }
}
