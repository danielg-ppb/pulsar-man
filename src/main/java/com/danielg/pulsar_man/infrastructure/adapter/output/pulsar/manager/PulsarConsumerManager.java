package com.danielg.pulsar_man.infrastructure.adapter.output.pulsar.manager;

import com.danielg.pulsar_man.domain.model.PulsarConsumer;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.PulsarConsumerRequest;
import lombok.Getter;
import org.apache.pulsar.client.api.Consumer;
import org.springframework.stereotype.Service;

@Service
@Getter
public class PulsarConsumerManager {
    private PulsarConsumer pulsarConsumer;

    public synchronized void initializePulsarConsumer(Consumer<?> consumer, PulsarConsumerRequest pulsarConsumerDto, String schemaFile) {
        this.pulsarConsumer = new PulsarConsumer(consumer, pulsarConsumerDto.getTopicName(), pulsarConsumerDto.getSubscriptionName(), pulsarConsumerDto.getSchemaType(), pulsarConsumerDto.getInitialPosition(), schemaFile);
    }

    public synchronized void setPulsarConsumerToNull(){
        this.pulsarConsumer = null;
    }
}
