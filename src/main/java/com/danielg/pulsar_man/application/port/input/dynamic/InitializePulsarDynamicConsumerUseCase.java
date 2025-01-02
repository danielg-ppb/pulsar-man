package com.danielg.pulsar_man.application.port.input.dynamic;

import com.google.protobuf.GeneratedMessageV3;
import org.apache.pulsar.client.api.Consumer;

public interface InitializePulsarDynamicConsumerUseCase {
    Consumer<?> startConsumer(String dynamicStateKey,
                              Class<? extends GeneratedMessageV3> schemaClass) throws Exception;
}
