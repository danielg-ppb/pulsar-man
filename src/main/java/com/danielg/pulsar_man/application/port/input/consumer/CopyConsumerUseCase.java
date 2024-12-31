package com.danielg.pulsar_man.application.port.input.consumer;

import com.danielg.pulsar_man.domain.model.PulsarConsumer;
import org.apache.pulsar.client.api.Consumer;

public interface CopyConsumerUseCase {
    Consumer<?> copyDynamicConsumer(PulsarConsumer pulsarConsumer);
}
