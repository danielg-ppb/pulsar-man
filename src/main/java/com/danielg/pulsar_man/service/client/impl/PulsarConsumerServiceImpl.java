package com.danielg.pulsar_man.service.client.impl;

import com.danielg.pulsar_man.dto.request.PulsarConsumerRequestDto;
import com.danielg.pulsar_man.service.client.PulsarConsumerService;
import com.danielg.pulsar_man.state.PulsarClientManager;
import com.danielg.pulsar_man.state.PulsarConsumerManager;
import com.danielg.pulsar_man.utils.PulsarSubcriptionUtils;
import com.danielg.pulsar_man.utils.SchemaProvider;
import jakarta.annotation.PreDestroy;
import org.apache.pulsar.client.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PulsarConsumerServiceImpl implements PulsarConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(PulsarConsumerServiceImpl.class);

    private final PulsarClientManager pulsarClientManagerState;
    private final PulsarConsumerManager pulsarConsumerState;

    public PulsarConsumerServiceImpl(PulsarClientManager pulsarClientManagerState, PulsarConsumerManager pulsarConsumerState) {
        this.pulsarClientManagerState = pulsarClientManagerState;
        this.pulsarConsumerState = pulsarConsumerState;
    }

    @Override
    public void initializeConsumer(PulsarConsumerRequestDto pulsarConsumerDto) throws PulsarClientException {
        if (this.pulsarConsumerState.getPulsarConsumer() != null) {
            this.closeConsumer();
        }

        this.pulsarConsumerState.initializePulsarConsumerState(this.pulsarClientManagerState.getPulsarClientProvider().getPulsarClient().newConsumer(SchemaProvider.getSchema(pulsarConsumerDto.getSchemaType()))
                .topic(pulsarConsumerDto.getTopicName())
                .subscriptionName(pulsarConsumerDto.getSubscriptionName())
                .subscriptionType(SubscriptionType.Shared)
                .subscriptionInitialPosition(PulsarSubcriptionUtils.pulsarInitialPositionFromString(pulsarConsumerDto.getInitialPosition()))
                .subscribe(), pulsarConsumerDto);

    }

    @Override
    public List<String> consumeMessages(Integer messageCount) throws PulsarClientException {
        logger.info("Consuming messages from topic: " + this.pulsarConsumerState.getPulsarConsumer().getTopicName());
        List<String> messages = new ArrayList<>();

        for (int i = 0; i < messageCount; i++) {
            Message<?> msg = this.pulsarConsumerState.getPulsarConsumer().getConsumer().receive(1, TimeUnit.SECONDS);
            if (msg == null) {
                break; // No more messages to consume
            }
            messages.add(msg.getValue().toString());
            this.pulsarConsumerState.getPulsarConsumer().getConsumer().acknowledge(msg);
        }
        return messages;
    }

    public synchronized void closeConsumer() {
        if (this.pulsarConsumerState.getPulsarConsumer() != null && this.pulsarConsumerState.getPulsarConsumer().getConsumer() != null) {
            try {
                this.pulsarConsumerState.getPulsarConsumer().getConsumer().close(); // Close the Pulsar consumer
            } catch (Exception e) {
                logger.error("Cannot close pulsar consumer", e);
            } finally {
                this.pulsarConsumerState.setPulsarConsumerToNull(); // Ensure the reference is cleared
            }
        }
    }

    @PreDestroy
    public void shutdown() {
        closeConsumer(); // Close the Pulsar client during shutdown
    }

}
