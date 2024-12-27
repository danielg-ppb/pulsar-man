package com.danielg.pulsar_man.infrastructure.adapter.input.rest.controller.client;

import com.danielg.pulsar_man.application.port.input.consumer.ConsumeMessagesUseCase;
import com.danielg.pulsar_man.application.port.input.consumer.InitializeConsumerUseCase;
import com.danielg.pulsar_man.application.port.input.consumer.InitializeDynamicConsumer;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.PulsarConsumerRequest;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.response.GenericResponse;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api/pulsar-consumer")
public class ConsumerController {
    private final InitializeConsumerUseCase initializeConsumerUseCase;
    private final ConsumeMessagesUseCase consumeMessagesUseCase;
    private final InitializeDynamicConsumer initializeDynamicConsumer;

    public ConsumerController(InitializeConsumerUseCase initializeConsumerUseCase, ConsumeMessagesUseCase consumeMessagesUseCase, InitializeDynamicConsumer initializeDynamicConsumer) {
        this.initializeConsumerUseCase = initializeConsumerUseCase;
        this.consumeMessagesUseCase = consumeMessagesUseCase;
        this.initializeDynamicConsumer = initializeDynamicConsumer;
    }

    @PostMapping("/initialize")
    public ResponseEntity<GenericResponse> initializeConsumer(@RequestBody PulsarConsumerRequest pulsarConsumerDto) {
        try {
            initializeConsumerUseCase.initializeConsumer(pulsarConsumerDto);
            GenericResponse response = new GenericResponse("Consumer initialized successfully");
            return ResponseEntity.ok(response);
        } catch (PulsarClientException e) {
            return ResponseEntity.status(500).build(); // Handle exceptions appropriately
        } catch (IllegalArgumentException e) {
            GenericResponse response = new GenericResponse("Consumer initialized successfully");
            return ResponseEntity.status(400).body(response); // Bad request for unsupported schema
        }
    }

    @PostMapping("/dynamic-initialize")
    public ResponseEntity<GenericResponse> initializeDynamicConsumer(@RequestParam String topicName, @RequestParam String subscriptionName,
                                                                     @RequestParam String schemaType, @RequestParam String subscriptionType,
                                                                     @RequestParam String initialPosition,
                                                                     @RequestParam(required = false) MultipartFile protoFile) {
        try {
            PulsarConsumerRequest pulsarConsumerDto = new PulsarConsumerRequest(topicName, subscriptionName, schemaType, subscriptionType, initialPosition);
            initializeDynamicConsumer.initializeDynamicConsumer(pulsarConsumerDto, protoFile);
            GenericResponse response = new GenericResponse("Dynamic consumer initialized successfully");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/consume")
    public ResponseEntity<List<String>> consumeMessages(@RequestParam Integer messageCount) {

        try {
            List<String> messages = consumeMessagesUseCase.consumeMessages(messageCount);
            return ResponseEntity.ok(messages);
        } catch (PulsarClientException e) {
            return ResponseEntity.status(500).build(); // Handle exceptions appropriately
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(List.of(e.getMessage())); // Bad request for unsupported schema
        }
    }


}
