package com.danielg.pulsar_man.controller.client;

import com.danielg.pulsar_man.dto.request.PulsarServiceUrlRequestDto;
import com.danielg.pulsar_man.service.client.PulsarClientProviderService;
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
    public ResponseEntity<String> setServiceUrl(@RequestBody PulsarServiceUrlRequestDto pulsarServiceUrlDto) {
        try {
            this.pulsarClientProviderService.initializeWithServiceUrl(pulsarServiceUrlDto.getServiceUrl());
            return ResponseEntity.ok("Service URL set successfully");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Error setting service URL");
        }
    }


}
