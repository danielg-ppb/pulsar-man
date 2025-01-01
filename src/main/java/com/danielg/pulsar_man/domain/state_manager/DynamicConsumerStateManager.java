package com.danielg.pulsar_man.domain.state_manager;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.pulsar.client.api.SubscriptionInitialPosition;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
public class DynamicConsumerStateManager {
    private String topicName;
    private String subscriptionName;
    private SubscriptionType subscriptionType;
    private SubscriptionInitialPosition initialPosition;
    private String schemaFile;
    private String outerClassName;
    private String mainInnerClassName;
}
