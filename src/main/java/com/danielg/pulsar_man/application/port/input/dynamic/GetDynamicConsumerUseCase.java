package com.danielg.pulsar_man.application.port.input.dynamic;

import com.danielg.pulsar_man.application.service.dynamic.DynamicConsumerSingleton;

public interface GetDynamicConsumerUseCase {
    DynamicConsumerSingleton getDynamicConsumerSingleton();
}
