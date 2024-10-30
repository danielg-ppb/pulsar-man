package com.danielg.pulsar_man.infrastructure.adapter.input.rest.controller.client;

import com.danielg.pulsar_man.application.port.input.provider.InitializeClientProviderUseCase;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.PulsarServiceUrlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/pulsar-provider")
public class PulsarClientProviderController {

    private final InitializeClientProviderUseCase initializeClientProviderUseCase;

    @Autowired
    public PulsarClientProviderController(InitializeClientProviderUseCase initializeClientProviderUseCase) {
        this.initializeClientProviderUseCase = initializeClientProviderUseCase;
    }

    @PostMapping("/service-url")
    public ResponseEntity<String> setServiceUrl(@RequestBody PulsarServiceUrlRequest pulsarServiceUrlDto) {
        try {
            this.initializeClientProviderUseCase.initializeWithServiceUrl(pulsarServiceUrlDto.getServiceUrl());
            return ResponseEntity.ok("Service URL set successfully");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Error setting service URL");
        }
    }


}
