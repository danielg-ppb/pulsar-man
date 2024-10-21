package com.danielg.pulsar_man.controller;

import com.danielg.pulsar_man.dto.PulsarServiceUrlDto;
import com.danielg.pulsar_man.service.PulsarClientProviderService;
import com.danielg.pulsar_man.state.InMemoryPulsarClientProviderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/pulsar-provider")
public class PulsarClientProviderController {

    private final PulsarClientProviderService pulsarClientProviderService;

    @Autowired
    public PulsarClientProviderController(PulsarClientProviderService pulsarClientProviderService) {
        this.pulsarClientProviderService = pulsarClientProviderService;
    }

    @PostMapping("/service-url")
    public ResponseEntity<String> setServiceUrl(@RequestBody PulsarServiceUrlDto pulsarServiceUrlDto) {
        try {
            this.pulsarClientProviderService.initializeWithServiceUrl(pulsarServiceUrlDto.getServiceUrl());
            return ResponseEntity.ok("Service URL set successfully");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Error setting service URL");
        }
    }


}
