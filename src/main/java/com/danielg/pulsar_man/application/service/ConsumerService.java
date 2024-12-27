package com.danielg.pulsar_man.application.service;

import com.danielg.pulsar_man.application.port.input.consumer.ConsumeMessagesUseCase;
import com.danielg.pulsar_man.application.port.input.consumer.InitializeConsumerUseCase;
import com.danielg.pulsar_man.application.port.input.consumer.InitializeDynamicConsumer;
import com.danielg.pulsar_man.application.port.input.file.UploadFileUseCase;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.PulsarConsumerRequest;
import com.danielg.pulsar_man.infrastructure.pulsar.factory.ClientFactory;
import com.danielg.pulsar_man.infrastructure.pulsar.factory.ConsumerFactory;
import com.danielg.pulsar_man.utils.PulsarSubcriptionUtils;
import com.danielg.pulsar_man.utils.SchemaProvider;
import jakarta.annotation.PreDestroy;
import org.apache.pulsar.client.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ConsumerService implements InitializeConsumerUseCase, ConsumeMessagesUseCase, InitializeDynamicConsumer {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    private final ClientFactory clientFactory;
    private final ConsumerFactory consumerFactory;
    private final UploadFileUseCase uploadFileUseCase;

    public ConsumerService(ClientFactory clientFactory, ConsumerFactory consumerFactory, UploadFileUseCase uploadFileUseCase) {
        this.clientFactory = clientFactory;
        this.consumerFactory = consumerFactory;
        this.uploadFileUseCase = uploadFileUseCase;
    }

    @Override
    public void initializeConsumer(PulsarConsumerRequest pulsarConsumerDto) throws PulsarClientException {
        if (this.consumerFactory.getPulsarConsumer() != null) {
            this.closeConsumer();
        }

        this.consumerFactory.initializePulsarConsumer(
                this.clientFactory.getPulsarClientProvider().getPulsarClient()
                        .newConsumer(SchemaProvider.getSchema(pulsarConsumerDto.getSchemaType()))
                        .topic(pulsarConsumerDto.getTopicName())
                        .subscriptionName(pulsarConsumerDto.getSubscriptionName())
                        .subscriptionType(SubscriptionType.valueOf(pulsarConsumerDto.getSubscriptionType()))
                        .subscriptionInitialPosition(PulsarSubcriptionUtils.pulsarInitialPositionFromString(pulsarConsumerDto.getInitialPosition()))
                        .subscribe(), pulsarConsumerDto, null);
    }

    public void initializeDynamicConsumer(PulsarConsumerRequest pulsarConsumerDto, MultipartFile protoFile) throws Exception {
        if (this.consumerFactory.getPulsarConsumer() != null) {
            this.closeConsumer();
        }

        String protoFileName = protoFile != null ? this.uploadFileUseCase.saveFile(protoFile).getFileName().toString() : null;

        Schema<?> schema = SchemaProvider.getSchema(pulsarConsumerDto.getSchemaType());

        if (pulsarConsumerDto.getSchemaType().equals("protobuf") && protoFileName != null) {
            this.consumerFactory.initializePulsarConsumer(
                    this.clientFactory.getPulsarClientProvider().getPulsarClient()
                            .newConsumer()
                            .topic(pulsarConsumerDto.getTopicName())
                            .subscriptionName(pulsarConsumerDto.getSubscriptionName())
                            .subscriptionType(SubscriptionType.Shared)
                            .subscriptionInitialPosition(PulsarSubcriptionUtils.pulsarInitialPositionFromString(pulsarConsumerDto.getInitialPosition()))
                            .subscribe(), pulsarConsumerDto, protoFileName);
        } else {
            this.initializeConsumer(pulsarConsumerDto);
        }

    }

    @Override
    public List<String> consumeMessages(Integer messageCount) throws PulsarClientException {
        logger.info("Consuming messages from topic: " + this.consumerFactory.getPulsarConsumer().getTopicName());
        List<String> messages = new ArrayList<>();

        for (int i = 0; i < messageCount; i++) {
            Message<?> msg = this.consumerFactory.getPulsarConsumer().getConsumer().receive(1, TimeUnit.SECONDS);
            if (msg == null) {
                break; // No more messages to consume
            }
            messages.add(msg.getValue().toString());
            this.consumerFactory.getPulsarConsumer().getConsumer().acknowledge(msg);
        }
        return messages;
    }

    public synchronized void closeConsumer() {
        if (this.consumerFactory.getPulsarConsumer() != null && this.consumerFactory.getPulsarConsumer().getConsumer() != null) {
            try {
                this.consumerFactory.getPulsarConsumer().getConsumer().close(); // Close the Pulsar consumer
            } catch (Exception e) {
                logger.error("Cannot close pulsar consumer", e);
            } finally {
                this.consumerFactory.setPulsarConsumerToNull(); // Ensure the reference is cleared
            }
        }
    }

    @PreDestroy
    public void shutdown() {
        closeConsumer(); // Close the Pulsar client during shutdown
    }
}
