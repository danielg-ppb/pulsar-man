package com.danielg.pulsar_man.config;

import com.danielg.pulsar_man.utils.PulsarSubcriptionUtils;
import com.danielg.pulsar_man.utils.SchemaProvider;
import lombok.Getter;
import org.apache.pulsar.client.api.*;

@Getter
public class PulsarConsumer {
    private final PulsarClientProvider pulsarClientProvider;
    private final String topicName;
    private final String subscriptionName;
    private final Schema<?> schemaProvider;
    private SubscriptionInitialPosition initialPosition;


    public PulsarConsumer(PulsarClientProvider pulsarClientProvider, String topicName, String subscriptionName, String schemaType, String initialPosition) {
        this.pulsarClientProvider = pulsarClientProvider;
        this.topicName = topicName;
        this.subscriptionName = subscriptionName;
        this.schemaProvider = SchemaProvider.getSchema(schemaType);
        this.initialPosition = PulsarSubcriptionUtils.pulsarInitialPositionFromString(initialPosition);

    }

    public Consumer<?> initializeConsumer() throws PulsarClientException {
        PulsarClient pulsarClient = pulsarClientProvider.getPulsarClient();
        return pulsarClient.newConsumer(schemaProvider)
                .topic(topicName)
                .subscriptionName(subscriptionName)
                .subscriptionType(SubscriptionType.Shared)
                .subscriptionInitialPosition(this.initialPosition)
                .subscribe();
    }
}
