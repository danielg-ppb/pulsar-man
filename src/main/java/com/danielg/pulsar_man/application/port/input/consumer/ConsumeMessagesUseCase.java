package com.danielg.pulsar_man.application.port.input.consumer;

import org.apache.pulsar.client.api.PulsarClientException;

import java.util.List;

public interface ConsumeMessagesUseCase {
    public List<String> consumeMessages(Integer messageCount) throws PulsarClientException;

}
