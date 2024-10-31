package com.danielg.pulsar_man.infrastructure.adapter.input.rest.controller.client;

import com.danielg.pulsar_man.application.port.input.consumer.ConsumeMessagesUseCase;
import com.danielg.pulsar_man.application.port.input.consumer.InitializeConsumerUseCase;
import com.danielg.pulsar_man.application.port.input.consumer.InitializeDynamicConsumer;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.PulsarConsumerRequest;
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
    public ResponseEntity<String> initializeConsumer(@RequestBody PulsarConsumerRequest pulsarConsumerDto) {
        try {
            initializeConsumerUseCase.initializeConsumer(pulsarConsumerDto);
            return ResponseEntity.ok("Consumer initialized successfully");
        } catch (PulsarClientException e) {
            return ResponseEntity.status(500).build(); // Handle exceptions appropriately
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage()); // Bad request for unsupported schema
        }
    }

    @PostMapping("/dynamic-initialize")
    public ResponseEntity<String> initializeDynamicConsumer(@RequestParam String topicName, @RequestParam String subscriptionName,
                                                            @RequestParam String schemaType, @RequestParam String initialPosition,
                                                            @RequestParam(required = false) MultipartFile protoFile) {
        try {
            PulsarConsumerRequest pulsarConsumerDto = new PulsarConsumerRequest(topicName, subscriptionName, schemaType, initialPosition);
            initializeDynamicConsumer.initializeDynamicConsumer(pulsarConsumerDto, protoFile);
            return ResponseEntity.ok("Dynamic consumer initialized successfully");
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
