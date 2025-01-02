package com.danielg.pulsar_man.application.port.input.dynamic;

import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.DynamicConsumerRequest;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.PulsarConsumerRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface CreateDynamicConsumerUseCase {
    void createDynamicConsumer(DynamicConsumerRequest request, MultipartFile protoFile) throws Exception;

    void createDynamicConsumer(DynamicConsumerRequest pulsarConsumerRequest, File protoFile);
}
