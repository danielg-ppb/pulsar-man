package com.danielg.pulsar_man.infrastructure.pulsar.factory;

import com.danielg.pulsar_man.domain.model.PulsarDynamicConsumer;
import com.danielg.pulsar_man.utils.PulsarSubcriptionUtils;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.stereotype.Component;

@Component
public class DynamicConsumerFactory {
    private PulsarDynamicConsumer pulsarDynamicConsumer;

    public PulsarDynamicConsumer createDynamicConsumer(
            String topicName,
            String subscriptionName,
            String subscriptionType,
            String initialPosition,
            String schemaFile,
            String outerClassName,
            String mainInnerClassName) {

        return PulsarDynamicConsumer.builder()
                .topicName(topicName)
                .subscriptionName(subscriptionName)
                .subscriptionType(SubscriptionType.valueOf(subscriptionType))
                .initialPosition(PulsarSubcriptionUtils.pulsarInitialPositionFromString(initialPosition))
                .schemaFile(schemaFile)
                .outerClassName(outerClassName)
                .mainInnerClassName(mainInnerClassName)
                .build();
    }
}
