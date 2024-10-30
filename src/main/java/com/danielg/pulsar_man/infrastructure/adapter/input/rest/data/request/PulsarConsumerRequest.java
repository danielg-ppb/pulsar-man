package com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PulsarConsumerRequest {
    private String topicName;
    private String subscriptionName;
    private String schemaType;
    private String initialPosition;

    public PulsarConsumerRequest(String topicName, String subscriptionName, String schemaType, String initialPosition) {
        this.topicName = topicName;
        this.subscriptionName = subscriptionName;
        this.schemaType = schemaType;
        this.initialPosition = initialPosition;
    }
}
