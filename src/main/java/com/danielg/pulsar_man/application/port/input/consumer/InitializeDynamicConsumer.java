package com.danielg.pulsar_man.application.port.input.consumer;

import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.PulsarConsumerRequest;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;

public interface InitializeDynamicConsumer {
    //void initializeDynamicConsumer(/*PulsarConsumerRequest pulsarConsumerDto*/) throws Exception;
    void initializeDynamicConsumer(PulsarConsumerRequest pulsarConsumerDto, MultipartFile protoFile) throws Exception;

}
