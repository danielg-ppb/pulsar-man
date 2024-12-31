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
    private final String subscriptionType;
    private final String schemaType;
    private SubscriptionInitialPosition initialPosition;
    private String schemaFile;


    public PulsarConsumer(Consumer<?> consumer,
                          String topicName,
                          String subscriptionName,
                          String subscriptionType,
                          String schemaType,
                          String initialPosition,
                          String schemaFile) {
        this.consumer = consumer;
        this.topicName = topicName;
        this.subscriptionName = subscriptionName;
        this.subscriptionType = subscriptionType;
        this.schemaType = schemaType;
        this.initialPosition = PulsarSubcriptionUtils.pulsarInitialPositionFromString(initialPosition);
        this.schemaFile = schemaFile;
    }


    public String toString() {
        return "PulsarConsumer{" +
                "consumer=" + consumer +
                ", topicName='" + topicName + '\'' +
                ", subscriptionName='" + subscriptionName + '\'' +
                ", subscriptionType='" + subscriptionType + '\'' +
                ", schemaType=" + schemaType +
                ", initialPosition=" + initialPosition +
                ", schemaFile='" + schemaFile + '\'' +
                '}';
    }
}
