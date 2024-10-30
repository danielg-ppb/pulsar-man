package com.danielg.pulsar_man.state;

import com.danielg.pulsar_man.domain.model.PulsarConsumer;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.PulsarConsumerRequest;
import lombok.Getter;
import org.apache.pulsar.client.api.Consumer;
import org.springframework.stereotype.Service;

@Service
@Getter
public class PulsarConsumerManager {
    private PulsarConsumer pulsarConsumer;

    public synchronized void initializePulsarConsumerState(Consumer<?> consumer, PulsarConsumerRequest pulsarConsumerDto) {
        this.pulsarConsumer = new PulsarConsumer(consumer, pulsarConsumerDto.getTopicName(), pulsarConsumerDto.getSubscriptionName(), pulsarConsumerDto.getSchemaType(), pulsarConsumerDto.getInitialPosition());
    }

    public synchronized void setPulsarConsumerToNull(){
        this.pulsarConsumer = null;
    }
}
