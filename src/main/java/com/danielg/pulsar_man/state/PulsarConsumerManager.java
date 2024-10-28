package com.danielg.pulsar_man.state;

import com.danielg.pulsar_man.model.PulsarConsumer;
import com.danielg.pulsar_man.dto.request.PulsarConsumerRequestDto;
import lombok.Getter;
import org.apache.pulsar.client.api.Consumer;
import org.springframework.stereotype.Service;

@Service
@Getter
public class PulsarConsumerManager {
    private PulsarConsumer pulsarConsumer;

    public synchronized void initializePulsarConsumerState(Consumer<?> consumer, PulsarConsumerRequestDto pulsarConsumerDto) {

        this.pulsarConsumer = new PulsarConsumer(consumer, pulsarConsumerDto.getTopicName(), pulsarConsumerDto.getSubscriptionName(), pulsarConsumerDto.getSchemaType(), pulsarConsumerDto.getInitialPosition());
    }

    public synchronized void setPulsarConsumerToNull(){
        this.pulsarConsumer = null;
    }
}
