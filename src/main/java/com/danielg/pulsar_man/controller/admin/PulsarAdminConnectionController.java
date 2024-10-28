package com.danielg.pulsar_man.controller.admin;

import com.danielg.pulsar_man.dto.request.PulsarServiceUrlRequestDto;
import com.danielg.pulsar_man.dto.response.PartitionListResponseDto;
import com.danielg.pulsar_man.dto.response.TopicListResponseDto;
import com.danielg.pulsar_man.service.admin.PulsarAdminConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/pulsar-admin")
public class PulsarAdminConnectionController {
    private final PulsarAdminConnectionService pulsarAdminConnectionService;

    @Autowired
    public PulsarAdminConnectionController(PulsarAdminConnectionService pulsarAdminConnectionService) {
        this.pulsarAdminConnectionService = pulsarAdminConnectionService;
    }

    @PostMapping("/service-url")
    public ResponseEntity<String> setServiceUrlForAdminTask(@RequestBody PulsarServiceUrlRequestDto pulsarServiceUrlDto) {
        try {
            this.pulsarAdminConnectionService.initializePulsarAdmin(pulsarServiceUrlDto.getServiceUrl());
            return ResponseEntity.ok("Service URL set successfully");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Error setting service URL");
        }
    }
}
