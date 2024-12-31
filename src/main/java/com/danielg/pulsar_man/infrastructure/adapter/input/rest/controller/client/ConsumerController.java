package com.danielg.pulsar_man.infrastructure.adapter.input.rest.controller.client;

import com.danielg.pulsar_man.application.port.input.consumer.ConsumeMessagesUseCase;
import com.danielg.pulsar_man.application.port.input.consumer.InitializeConsumerUseCase;
import com.danielg.pulsar_man.application.port.input.consumer.InitializeDynamicConsumer;
import com.danielg.pulsar_man.application.port.input.dynamic.CreateDynamicConsumerUseCase;
import com.danielg.pulsar_man.application.port.input.dynamic.InitializePulsarDynamicConsumerUseCase;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.DynamicConsumerRequest;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.PulsarConsumerRequest;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.response.GenericResponse;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/pulsar-consumer")
public class ConsumerController {
    private final InitializeConsumerUseCase initializeConsumerUseCase;
    private final ConsumeMessagesUseCase consumeMessagesUseCase;
    private final CreateDynamicConsumerUseCase createDynamicConsumerUseCase;

    public ConsumerController(InitializeConsumerUseCase initializeConsumerUseCase,
                              ConsumeMessagesUseCase consumeMessagesUseCase,
                              CreateDynamicConsumerUseCase createDynamicConsumerUseCase) {
        this.initializeConsumerUseCase = initializeConsumerUseCase;
        this.consumeMessagesUseCase = consumeMessagesUseCase;
        this.createDynamicConsumerUseCase = createDynamicConsumerUseCase;
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
    public ResponseEntity<GenericResponse> initializeDynamicConsumer(@Valid @ModelAttribute DynamicConsumerRequest request,
                                                                     @RequestParam(required = false) MultipartFile protoFile) {
        try {
            createDynamicConsumerUseCase.createDynamicConsumer(request, protoFile);
            GenericResponse response = new GenericResponse("Dynamic consumer initialized successfully");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
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
