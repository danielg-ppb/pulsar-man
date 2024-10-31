package com.danielg.pulsar_man.application.port.input.consumer;

import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.PulsarConsumerRequest;
import org.springframework.web.multipart.MultipartFile;


public interface InitializeDynamicConsumer {
    void initializeDynamicConsumer(PulsarConsumerRequest pulsarConsumerDto, MultipartFile protoFile) throws Exception;

}
