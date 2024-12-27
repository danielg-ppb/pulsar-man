package com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class PulsarConsumerRequest {
    private String topicName;
    private String subscriptionName;
    private String schemaType;
    private String subscriptionType;
    private String initialPosition;
}
