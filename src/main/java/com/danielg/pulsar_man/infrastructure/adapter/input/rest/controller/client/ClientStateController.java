package com.danielg.pulsar_man.infrastructure.adapter.input.rest.controller.client;

import com.danielg.pulsar_man.application.port.input.client.GetClientStateUseCase;
import com.danielg.pulsar_man.application.port.input.consumer.InitializeConsumerUseCase;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.response.GenericResponse;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/client-state")
public class ClientStateController {
    private final GetClientStateUseCase getClientStateUseCase;

    public ClientStateController(GetClientStateUseCase getClientStateUseCase) {
        this.getClientStateUseCase = getClientStateUseCase;
    }


    @GetMapping
    public ResponseEntity<GenericResponse> consumeMessages() {

        try {
            GenericResponse messages = new GenericResponse(getClientStateUseCase.getClientState());
            return ResponseEntity.ok(messages);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new GenericResponse(e.getMessage())); // Bad request for unsupported schema
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
