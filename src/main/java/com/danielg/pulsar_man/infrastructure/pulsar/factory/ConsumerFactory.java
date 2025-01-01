package com.danielg.pulsar_man.infrastructure.pulsar.factory;

import com.danielg.pulsar_man.domain.model.PulsarConsumer;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.PulsarConsumerRequest;
import lombok.Getter;
import org.apache.pulsar.client.api.Consumer;
import org.springframework.stereotype.Service;

@Service
@Getter
public class ConsumerFactory {
    private PulsarConsumer pulsarConsumer;

    public synchronized PulsarConsumer initializePulsarConsumer(Consumer<?> consumer,
                                                                PulsarConsumerRequest pulsarConsumerDto,
                                                                String schemaFile) {
        this.pulsarConsumer = new PulsarConsumer(consumer,
                pulsarConsumerDto.getTopicName(),
                pulsarConsumerDto.getSubscriptionName(),
                pulsarConsumerDto.getSubscriptionType(),
                pulsarConsumerDto.getSchemaType(),
                pulsarConsumerDto.getInitialPosition(), schemaFile);
        return this.pulsarConsumer;
    }

    public synchronized void setPulsarConsumerToNull() {
        this.pulsarConsumer = null;
    }
}
