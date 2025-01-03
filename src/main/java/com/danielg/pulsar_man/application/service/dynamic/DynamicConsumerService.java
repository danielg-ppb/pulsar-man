package com.danielg.pulsar_man.application.service.dynamic;

import com.danielg.pulsar_man.application.port.input.dynamic.CreateDynamicConsumerUseCase;
import com.danielg.pulsar_man.application.port.input.dynamic.InitializePulsarDynamicConsumerUseCase;
import com.danielg.pulsar_man.domain.model.PulsarDynamicConsumer;
import com.danielg.pulsar_man.domain.repository.DynamicConsumerRepository;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.DynamicConsumerRequest;
import com.danielg.pulsar_man.infrastructure.protoc.ProtocExecutor;
import com.danielg.pulsar_man.infrastructure.pulsar.factory.ClientFactory;
import com.danielg.pulsar_man.infrastructure.pulsar.factory.DynamicConsumerFactory;
import com.danielg.pulsar_man.utils.FileUtils;
import com.danielg.pulsar_man.utils.PulsarKeyUtils;
import com.google.protobuf.GeneratedMessageV3;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class DynamicConsumerService implements CreateDynamicConsumerUseCase,
        InitializePulsarDynamicConsumerUseCase {
    private final ClientFactory clientFactory;
    private final DynamicConsumerFactory dynamicConsumerFactory;
    private final DynamicConsumerRepository dynamicConsumerRepository;
    private final ProtocExecutor protocExecutor;
    private PulsarDynamicConsumer pulsarDynamicConsumer;

    public DynamicConsumerService(ClientFactory clientFactory,
                                  DynamicConsumerFactory dynamicConsumerFactory,
                                  DynamicConsumerRepository dynamicConsumerRepository,
                                  ProtocExecutor protocExecutor) {
        this.clientFactory = clientFactory;
        this.dynamicConsumerFactory = dynamicConsumerFactory;
        this.dynamicConsumerRepository = dynamicConsumerRepository;
        this.protocExecutor = protocExecutor;
    }

    @Override
    public void createDynamicConsumer(DynamicConsumerRequest pulsarConsumerRequest, MultipartFile protoFile) {
        try {
            PulsarDynamicConsumer pulsarDynamicConsumer = createPulsarDynamicConsumerObject(pulsarConsumerRequest,
                    protoFile.getOriginalFilename());

            this.dynamicConsumerRepository
                    .saveState(PulsarKeyUtils
                                    .generateKey(
                                            pulsarConsumerRequest.getTopicName(),
                                            pulsarConsumerRequest.getSubscriptionName()),
                            pulsarDynamicConsumer);

            File savedFile = FileUtils.saveMultipartFile(protoFile);
            protocExecutor.generateJavaClassesFromProto(List.of(savedFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createDynamicConsumer(DynamicConsumerRequest pulsarConsumerRequest,
                                      File consumerSchemaFile, List<File> protoFiles) {
        //TODO: add logic to generate java classes from multiple proto files
        try {
            PulsarDynamicConsumer pulsarDynamicConsumer = createPulsarDynamicConsumerObject(pulsarConsumerRequest,
                    consumerSchemaFile.getName());

            this.dynamicConsumerRepository
                    .saveState(PulsarKeyUtils
                                    .generateKey(
                                            pulsarConsumerRequest.getTopicName(),
                                            pulsarConsumerRequest.getSubscriptionName()),
                            pulsarDynamicConsumer);

            protocExecutor.generateJavaClassesFromProto(List.of(consumerSchemaFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private PulsarDynamicConsumer createPulsarDynamicConsumerObject(DynamicConsumerRequest pulsarConsumerRequest, String protoFile) {
        return this.dynamicConsumerFactory.createDynamicConsumer(
                pulsarConsumerRequest.getTopicName(),
                pulsarConsumerRequest.getSubscriptionName(),
                pulsarConsumerRequest.getSubscriptionType(),
                pulsarConsumerRequest.getInitialPosition(),
                protoFile,
                pulsarConsumerRequest.getOuterClassName(),
                pulsarConsumerRequest.getMainInnerClassName()
        );
    }

    public Consumer<?> startConsumer(
            String dynamicStateKey,
            Class<? extends GeneratedMessageV3> schemaClass) throws PulsarClientException {

        PulsarDynamicConsumer pulsarDynamicConsumer = this.dynamicConsumerRepository.getState(dynamicStateKey);

        Schema<?> schema = schemaClass != null ? Schema.PROTOBUF(schemaClass) : Schema.AUTO_CONSUME();
        return this.clientFactory.getPulsarClientProvider().getPulsarClient()
                .newConsumer(schema)
                .topic(pulsarDynamicConsumer.getTopicName())
                .subscriptionName(pulsarDynamicConsumer.getSubscriptionName())
                .subscriptionType(pulsarDynamicConsumer.getSubscriptionType())
                .subscriptionInitialPosition(pulsarDynamicConsumer.getInitialPosition())
                .subscribe();
    }
}
