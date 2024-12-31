package com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class DynamicConsumerRequest {
    private String topicName;
    private String subscriptionName;
    private String schemaType;
    private String subscriptionType;
    private String initialPosition;
    private String outerClassName;
    private String mainInnerClassName;
}
