package com.danielg.pulsar_man.application.port.input.dynamic;

import org.apache.pulsar.client.api.PulsarClientException;

public interface CloseRealTimePulsaMessagesUseCase {
    void closeConnection();
}
