package com.danielg.pulsar_man.service.client;

import com.danielg.pulsar_man.dto.request.PulsarConsumerRequestDto;
import org.apache.pulsar.client.api.PulsarClientException;

import java.util.List;

public interface PulsarConsumerService {
    public List<String> consumeMessages(Integer messageCount) throws PulsarClientException;
    public void initializeConsumer(PulsarConsumerRequestDto pulsarConsumerDto) throws PulsarClientException;
}
