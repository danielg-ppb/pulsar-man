package com.danielg.pulsar_man.infrastructure.adapter.input.rest.controller.admin;

import com.danielg.pulsar_man.application.port.input.connetion.InitializePulsarAdminConnectionUseCase;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.PulsarServiceUrlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/pulsar-admin")
public class PulsarAdminConnectionController {
    private final InitializePulsarAdminConnectionUseCase initializePulsarAdminConnectionUseCase;

    @Autowired
    public PulsarAdminConnectionController(InitializePulsarAdminConnectionUseCase initializePulsarAdminConnectionUseCase) {
        this.initializePulsarAdminConnectionUseCase = initializePulsarAdminConnectionUseCase;
    }

    @PostMapping("/service-url")
    public ResponseEntity<String> initializePulsarAdminConnection(@RequestBody PulsarServiceUrlRequest pulsarServiceUrlDto) {
        try {
            this.initializePulsarAdminConnectionUseCase.initializePulsarAdmin(pulsarServiceUrlDto.getServiceUrl());
            return ResponseEntity.ok("Service URL set successfully");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Error setting service URL");
        }
    }
}
