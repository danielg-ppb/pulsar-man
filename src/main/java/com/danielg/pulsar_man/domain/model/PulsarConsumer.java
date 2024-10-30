package com.danielg.pulsar_man.domain.model;

import com.danielg.pulsar_man.utils.PulsarSubcriptionUtils;
import com.danielg.pulsar_man.utils.SchemaProvider;
import lombok.Getter;
import org.apache.pulsar.client.api.*;

@Getter
public class PulsarConsumer {
    private final Consumer<?> consumer;
    private final String topicName;
    private final String subscriptionName;
    private final Schema<?> schemaProvider;
    private SubscriptionInitialPosition initialPosition;
    private String schemaFile;


    public PulsarConsumer(Consumer<?> consumer, String topicName, String subscriptionName, String schemaType, String initialPosition, String schemaFile) {
        this.consumer = consumer;
        this.topicName = topicName;
        this.subscriptionName = subscriptionName;
        this.schemaProvider = SchemaProvider.getSchema(schemaType);
        this.initialPosition = PulsarSubcriptionUtils.pulsarInitialPositionFromString(initialPosition);
        this.schemaFile = schemaFile;

    }
}
