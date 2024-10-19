package com.danielg.pulsar_man.controller;

import com.danielg.pulsar_man.dto.PulsarConsumerDto;
import com.danielg.pulsar_man.service.PulsarConsumerService;
import com.danielg.pulsar_man.utils.SchemaProvider;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/pulsar-consumer")
public class PulsarConsumerController {
    private final PulsarConsumerService pulsarConsumerService;

    public PulsarConsumerController(PulsarConsumerService pulsarConsumerService) {
        this.pulsarConsumerService = pulsarConsumerService;
    }

    @PostMapping("/initialize")
    public ResponseEntity<String> initializeConsumer(@RequestBody PulsarConsumerDto pulsarConsumerDto) {
        try {
            pulsarConsumerService.initializeConsumer(pulsarConsumerDto.getTopicName(), pulsarConsumerDto.getSubscriptionName(), pulsarConsumerDto.getSchemaType());
            return ResponseEntity.ok("Consumer initialized successfully");
        } catch (PulsarClientException e) {
            return ResponseEntity.status(500).build(); // Handle exceptions appropriately
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage()); // Bad request for unsupported schema
        }
    }

    @GetMapping("/consume")
    public ResponseEntity<List<String>> consumeMessages(@RequestParam Integer messageCount) {

        try {
            List<String> messages = pulsarConsumerService.consumeMessages(messageCount);
            return ResponseEntity.ok(messages);
        } catch (PulsarClientException e) {
            return ResponseEntity.status(500).build(); // Handle exceptions appropriately
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(List.of(e.getMessage())); // Bad request for unsupported schema
        }
    }


}
