package com.danielg.pulsar_man.service.impl;

import com.danielg.pulsar_man.dto.PulsarConsumerDto;
import com.danielg.pulsar_man.service.PulsarConsumerService;
import com.danielg.pulsar_man.state.InMemoryPulsarClientProviderState;
import com.danielg.pulsar_man.state.InMemoryPulsarConsumerState;
import com.danielg.pulsar_man.utils.PulsarSubcriptionUtils;
import com.danielg.pulsar_man.utils.SchemaProvider;
import org.apache.pulsar.client.api.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PulsarConsumerServiceImpl implements PulsarConsumerService {
    private final InMemoryPulsarClientProviderState pulsarClientProviderState;
    private final InMemoryPulsarConsumerState pulsarConsumerState;

    public PulsarConsumerServiceImpl(InMemoryPulsarClientProviderState pulsarClientProviderState, InMemoryPulsarConsumerState pulsarConsumerState) {
        this.pulsarClientProviderState = pulsarClientProviderState;
        this.pulsarConsumerState = pulsarConsumerState;
    }

    @Override
    public void initializeConsumer(PulsarConsumerDto pulsarConsumerDto) throws PulsarClientException {
        if (this.pulsarConsumerState.getPulsarConsumer() != null) {
            this.pulsarConsumerState.getPulsarConsumer().getConsumer().close();
        }

        this.pulsarConsumerState.initializePulsarConsumerState(this.pulsarClientProviderState.getPulsarClientProvider().getPulsarClient().newConsumer(SchemaProvider.getSchema(pulsarConsumerDto.getSchemaType()))
                .topic(pulsarConsumerDto.getTopicName())
                .subscriptionName(pulsarConsumerDto.getSubscriptionName())
                .subscriptionType(SubscriptionType.Shared)
                .subscriptionInitialPosition(PulsarSubcriptionUtils.pulsarInitialPositionFromString(pulsarConsumerDto.getInitialPosition()))
                .subscribe(), pulsarConsumerDto);

    }

    @Override
    public List<String> consumeMessages(Integer messageCount) throws PulsarClientException {
        List<String> messages = new ArrayList<>();

        for (int i = 0; i < messageCount; i++) {
            Message<?> msg = this.pulsarConsumerState.getPulsarConsumer().getConsumer().receive(1, TimeUnit.SECONDS);
            if (msg == null) {
                break; // No more messages to consume
            }
            messages.add(msg.getValue().toString());
            this.pulsarConsumerState.getPulsarConsumer().getConsumer().acknowledge(msg);
        }

        //pulsarConsumer.close();
        return messages;
    }

}
