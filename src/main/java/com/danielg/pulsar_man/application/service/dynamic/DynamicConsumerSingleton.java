package com.danielg.pulsar_man.application.service.dynamic;

import lombok.Getter;
import lombok.Setter;
import org.apache.pulsar.client.api.SubscriptionInitialPosition;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class DynamicConsumerSingleton {
    private String topicName;
    private String subscriptionName;
    private SubscriptionType subscriptionType;
    private SubscriptionInitialPosition initialPosition;
    private String schemaFile;
    private String outerClassName;
    private String mainInnerClassName;
}
