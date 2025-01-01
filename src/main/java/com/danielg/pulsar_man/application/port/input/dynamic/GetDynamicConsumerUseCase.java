package com.danielg.pulsar_man.application.port.input.dynamic;

import com.danielg.pulsar_man.domain.model.PulsarDynamicConsumer;

public interface GetDynamicConsumerUseCase {
    PulsarDynamicConsumer getDynamicConsumerSingleton();
}
