package com.danielg.pulsar_man.application.service.dynamic;

import com.danielg.pulsar_man.application.port.input.dynamic.CreateDynamicConsumerUseCase;
import com.danielg.pulsar_man.application.port.input.dynamic.GetDynamicConsumerUseCase;
import com.danielg.pulsar_man.application.port.input.dynamic.InitializePulsarDynamicConsumerUseCase;
import com.danielg.pulsar_man.domain.model.PulsarDynamicConsumer;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.DynamicConsumerRequest;
import com.danielg.pulsar_man.infrastructure.protoc.ProtocExecutor;
import com.danielg.pulsar_man.infrastructure.pulsar.factory.ClientFactory;
import com.danielg.pulsar_man.infrastructure.pulsar.factory.DynamicConsumerFactory;
import com.danielg.pulsar_man.utils.FileUtils;
import com.google.protobuf.GeneratedMessageV3;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class DynamicConsumerService implements CreateDynamicConsumerUseCase, GetDynamicConsumerUseCase,
        InitializePulsarDynamicConsumerUseCase {
    private final ClientFactory clientFactory;
    private final DynamicConsumerFactory dynamicConsumerFactory;
    private final ProtocExecutor protocExecutor;
    private Consumer<?> consumer;
    private PulsarDynamicConsumer pulsarDynamicConsumer;

    public DynamicConsumerService(ClientFactory clientFactory,
                                  DynamicConsumerFactory dynamicConsumerFactory,
                                  ProtocExecutor protocExecutor) {
        this.clientFactory = clientFactory;
        this.dynamicConsumerFactory = dynamicConsumerFactory;
        this.protocExecutor = protocExecutor;
    }

    @Override
    public void createDynamicConsumer(DynamicConsumerRequest pulsarConsumerRequest, MultipartFile protoFile) {
        try {
            this.pulsarDynamicConsumer = this.dynamicConsumerFactory.createDynamicConsumer(
                    pulsarConsumerRequest.getTopicName(),
                    pulsarConsumerRequest.getSubscriptionName(),
                    pulsarConsumerRequest.getSubscriptionType(),
                    pulsarConsumerRequest.getInitialPosition(),
                    protoFile.getOriginalFilename(),
                    pulsarConsumerRequest.getOuterClassName(),
                    pulsarConsumerRequest.getMainInnerClassName()
            );

            File savedFile = FileUtils.saveMultipartFile(protoFile);
            protocExecutor.generateJavaClassesFromProto(savedFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PulsarDynamicConsumer getDynamicConsumerSingleton() {
        return this.pulsarDynamicConsumer;
    }

    public Consumer<?> startConsumer(Class<? extends GeneratedMessageV3> schemaClass) throws PulsarClientException {
        if (this.consumer != null && this.consumer.isConnected()) {
            this.consumer.close();
        }

        Schema<?> schema = schemaClass != null ? Schema.PROTOBUF(schemaClass) : Schema.AUTO_CONSUME();
        this.consumer = this.clientFactory.getPulsarClientProvider().getPulsarClient()
                .newConsumer(schema)
                .topic(pulsarDynamicConsumer.getTopicName())
                .subscriptionName(pulsarDynamicConsumer.getSubscriptionName())
                .subscriptionType(pulsarDynamicConsumer.getSubscriptionType())
                .subscriptionInitialPosition(pulsarDynamicConsumer.getInitialPosition())
                .subscribe();

        return this.consumer;
    }

    public void closeConsumer() throws PulsarClientException {
        this.consumer.close();
    }
}
