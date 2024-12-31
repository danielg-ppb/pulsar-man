package com.danielg.pulsar_man.domain.model;

import lombok.Getter;

@Getter
public class DynamicConsumer {
    private String topic;
    private String subscriptionName;
    private String subscriptionType;
    private String schemaType;
    private String subscriptionInitialPosition;
}
