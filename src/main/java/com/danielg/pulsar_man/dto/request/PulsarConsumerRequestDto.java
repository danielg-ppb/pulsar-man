package com.danielg.pulsar_man.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PulsarConsumerRequestDto {
    private String topicName;
    private String subscriptionName;
    private String schemaType;
    private String initialPosition;
}
