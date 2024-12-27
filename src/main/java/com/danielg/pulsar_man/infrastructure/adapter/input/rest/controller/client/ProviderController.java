package com.danielg.pulsar_man.infrastructure.adapter.input.rest.controller.client;

import com.danielg.pulsar_man.application.port.input.provider.InitializeClientProviderUseCase;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.PulsarServiceUrlRequest;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.response.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/pulsar-provider")
public class ProviderController {

    private final InitializeClientProviderUseCase initializeClientProviderUseCase;

    @Autowired
    public ProviderController(InitializeClientProviderUseCase initializeClientProviderUseCase) {
        this.initializeClientProviderUseCase = initializeClientProviderUseCase;
    }

    @PostMapping("/service-url")
    public ResponseEntity<GenericResponse> setServiceUrl(@RequestBody PulsarServiceUrlRequest pulsarServiceUrlDto) {
        try {
            this.initializeClientProviderUseCase.initializeWithServiceUrl(pulsarServiceUrlDto);
            GenericResponse response = new GenericResponse("Service URL set successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println(e);
            GenericResponse response = new GenericResponse(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }


}
