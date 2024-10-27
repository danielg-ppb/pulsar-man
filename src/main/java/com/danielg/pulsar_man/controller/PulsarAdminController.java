package com.danielg.pulsar_man.controller;

import com.danielg.pulsar_man.dto.PulsarServiceUrlDto;
import com.danielg.pulsar_man.dto.TopicListDto;
import com.danielg.pulsar_man.service.PulsarAdminService;
import com.danielg.pulsar_man.service.PulsarClientProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/pulsar-admin")
public class PulsarAdminController {
    private final PulsarAdminService pulsarAdminService;

    @Autowired
    public PulsarAdminController(PulsarAdminService pulsarAdminService) {
        this.pulsarAdminService = pulsarAdminService;
    }

    @PostMapping("/service-url")
    public ResponseEntity<String> setServiceUrlForAdminTask(@RequestBody PulsarServiceUrlDto pulsarServiceUrlDto) {
        try {
            this.pulsarAdminService.initializePulsarAdmin(pulsarServiceUrlDto.getServiceUrl());
            return ResponseEntity.ok("Service URL set successfully");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Error setting service URL");
        }
    }

    @GetMapping("/topics")
    public TopicListDto listTopics() {
        return this.pulsarAdminService.listTopics("public", "default");
    }
}
