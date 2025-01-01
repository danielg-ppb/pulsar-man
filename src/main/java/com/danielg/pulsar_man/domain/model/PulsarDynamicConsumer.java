package com.danielg.pulsar_man.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.pulsar.client.api.SubscriptionInitialPosition;
import org.apache.pulsar.client.api.SubscriptionType;

@Getter
@AllArgsConstructor
@Builder
public class PulsarDynamicConsumer {
    private String topicName;
    private String subscriptionName;
    private SubscriptionType subscriptionType;
    private SubscriptionInitialPosition initialPosition;
    private String schemaFile;
    private String outerClassName;
    private String mainInnerClassName;
}
