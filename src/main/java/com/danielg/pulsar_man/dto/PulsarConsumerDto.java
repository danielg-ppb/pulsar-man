package com.danielg.pulsar_man.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Data
public class PulsarConsumerDto {
    private String topicName;
    private String subscriptionName;
    private String schemaType;
    private String initialPosition;
}
