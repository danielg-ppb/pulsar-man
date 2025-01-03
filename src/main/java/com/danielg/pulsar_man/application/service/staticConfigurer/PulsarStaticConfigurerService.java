package com.danielg.pulsar_man.application.service.staticConfigurer;

import com.danielg.pulsar_man.application.service.dynamic.DynamicConsumerService;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.DynamicConsumerRequest;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.PulsarServiceUrlRequest;
import com.danielg.pulsar_man.infrastructure.pulsar.factory.ClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.util.List;

@Service
public class PulsarStaticConfigurerService {
    private final ClientFactory clientFactory;
    private final DynamicConsumerService dynamicConsumerService;

    @Value("${static.pulsar.url}")
    private String serviceUrl;

    @Value("${static.pulsar.token}")
    private String token;

    @Value("${static.pulsar.topicName}")
    private String topicName;

    @Value("${static.pulsar.subscriptionName}")
    private String subscriptionName;

    @Value("${static.pulsar.subscriptionType}")
    private String subscriptionType;

    @Value("${static.pulsar.schemaType}")
    private String schemaType;

    @Value("${static.pulsar.initialPosition}")
    private String initialPosition;

    @Value("${static.schema.protoFile}")
    private String protoFile;

    @Value("${static.schema.outerClassName}")
    private String outerClassName;

    @Value("${static.schema.mainInnerClassName}")
    private String mainInnerClassName;


    public PulsarStaticConfigurerService(ClientFactory clientFactory, DynamicConsumerService dynamicConsumerService) {
        this.clientFactory = clientFactory;
        this.dynamicConsumerService = dynamicConsumerService;
    }

    public void configure() {
        PulsarServiceUrlRequest pulsarServiceUrlRequest =
                new PulsarServiceUrlRequest(serviceUrl, token);
        this.clientFactory.initializePulsarClientProvider(pulsarServiceUrlRequest);

        DynamicConsumerRequest dynamicConsumerRequest = DynamicConsumerRequest.builder()
                .topicName(topicName)
                .subscriptionName(subscriptionName)
                .subscriptionType(subscriptionType)
                .schemaType(schemaType)
                .initialPosition(initialPosition)
                .outerClassName(outerClassName)
                .mainInnerClassName(mainInnerClassName)
                .build();
        File loadedFile = loadFile();
        this.dynamicConsumerService.createDynamicConsumer(dynamicConsumerRequest, loadedFile, List.of(loadedFile));

    }

    private File loadFile() {
        ClassLoader classLoader = PulsarStaticConfigurerService.class.getClassLoader();
        URL resourceUrl = classLoader.getResource("uploads/" + protoFile);

        if (resourceUrl != null) {
            return new File(resourceUrl.getFile());
        } else {
            throw new RuntimeException("File not found");
        }
    }


}
