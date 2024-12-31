package com.danielg.pulsar_man.application.service.dynamic;

import com.danielg.pulsar_man.application.port.input.dynamic.CreateDynamicConsumerUseCase;
import com.danielg.pulsar_man.application.port.input.dynamic.GetDynamicConsumerUseCase;
import com.danielg.pulsar_man.application.port.input.dynamic.InitializePulsarDynamicConsumerUseCase;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.DynamicConsumerRequest;
import com.danielg.pulsar_man.infrastructure.protoc.ProtocExecutor;
import com.danielg.pulsar_man.infrastructure.pulsar.factory.ClientFactory;
import com.danielg.pulsar_man.utils.FileUtils;
import com.danielg.pulsar_man.utils.PulsarSubcriptionUtils;
import com.google.protobuf.GeneratedMessageV3;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class DynamicConsumerService implements CreateDynamicConsumerUseCase, GetDynamicConsumerUseCase,
        InitializePulsarDynamicConsumerUseCase {
    private final ClientFactory clientFactory;
    private final DynamicConsumerSingleton dynamicConsumerSingleton;
    private final ProtocExecutor protocExecutor;
    private Consumer<?> consumer;

    public DynamicConsumerService(ClientFactory clientFactory,
                                  DynamicConsumerSingleton dynamicConsumerSingleton,
                                  ProtocExecutor protocExecutor) {
        this.clientFactory = clientFactory;
        this.dynamicConsumerSingleton = dynamicConsumerSingleton;
        this.protocExecutor = protocExecutor;
    }

    @Override
    public void createDynamicConsumer(DynamicConsumerRequest pulsarConsumerRequest, MultipartFile protoFile) {
        try {
            dynamicConsumerSingleton.setTopicName(pulsarConsumerRequest.getTopicName());
            dynamicConsumerSingleton.setSubscriptionName(pulsarConsumerRequest.getSubscriptionName());
            dynamicConsumerSingleton.setSubscriptionType(
                    SubscriptionType.valueOf(pulsarConsumerRequest.getSubscriptionType()));
            dynamicConsumerSingleton.setInitialPosition(PulsarSubcriptionUtils.pulsarInitialPositionFromString(
                    pulsarConsumerRequest.getInitialPosition()));
            dynamicConsumerSingleton.setSchemaFile(protoFile.getOriginalFilename());
            dynamicConsumerSingleton.setOuterClassName(pulsarConsumerRequest.getOuterClassName());
            dynamicConsumerSingleton.setMainInnerClassName(pulsarConsumerRequest.getMainInnerClassName());

            File savedFile = FileUtils.saveMultipartFile(protoFile);
            //ProtoUtils.generateJavaFromProto(savedFile);
            protocExecutor.generateJavaClassesFromProto(savedFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DynamicConsumerSingleton getDynamicConsumerSingleton() {
        return dynamicConsumerSingleton;
    }

    public Consumer<?> startConsumer(Class<? extends GeneratedMessageV3> schemaClass) throws PulsarClientException {
        if (this.consumer != null && this.consumer.isConnected()) {
            this.consumer.close();
        }

        Schema<?> schema = schemaClass != null ? Schema.PROTOBUF(schemaClass) : Schema.AUTO_CONSUME();
        this.consumer = this.clientFactory.getPulsarClientProvider().getPulsarClient()
                .newConsumer(schema)
                .topic(dynamicConsumerSingleton.getTopicName())
                .subscriptionName(dynamicConsumerSingleton.getSubscriptionName())
                .subscriptionType(dynamicConsumerSingleton.getSubscriptionType())
                .subscriptionInitialPosition(dynamicConsumerSingleton.getInitialPosition())
                .subscribe();

        return this.consumer;
    }

    public void closeConsumer() throws PulsarClientException {
        this.consumer.close();
    }
}
