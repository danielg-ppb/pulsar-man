package com.danielg.pulsar_man.controller;

import com.danielg.pulsar_man.config.PulsarClientProvider;
import com.danielg.pulsar_man.dto.PulsarServiceUrlDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/pulsar-provider")
public class PulsarClientProviderController {

    private final PulsarClientProvider pulsarClientProvider;

    @Autowired
    public PulsarClientProviderController(PulsarClientProvider pulsarClientProvider) {
        this.pulsarClientProvider = pulsarClientProvider;
    }

    @PostMapping("/service-url")
    public ResponseEntity<String> getAllTopics(@RequestBody PulsarServiceUrlDto pulsarServiceUrlDto) {
        try {
            this.pulsarClientProvider.setServiceUrl(pulsarServiceUrlDto.getServiceUrl());
            return ResponseEntity.ok("Service URL set successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error setting service URL");
        }
    }


}
