package com.danielg.pulsar_man.controller.admin;

import com.danielg.pulsar_man.dto.request.PulsarServiceUrlRequestDto;
import com.danielg.pulsar_man.dto.response.PartitionListResponseDto;
import com.danielg.pulsar_man.dto.response.PartitionNumberResponseDto;
import com.danielg.pulsar_man.dto.response.TopicListResponseDto;
import com.danielg.pulsar_man.service.admin.PulsarAdminConnectionService;
import com.danielg.pulsar_man.service.admin.PulsarAdminPartitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/pulsar-admin/partition")
public class PulsarAdminPartitionController {
    private final PulsarAdminPartitionService pulsarAdminPartitionService;

    @Autowired
    public PulsarAdminPartitionController(PulsarAdminPartitionService pulsarAdminPartitionService) {
        this.pulsarAdminPartitionService = pulsarAdminPartitionService;
    }

    @GetMapping
    public PartitionListResponseDto listPartitions(@RequestParam String tenant, @RequestParam String namespace, @RequestParam String topic) {
        //public and default
        return this.pulsarAdminPartitionService.listPartitions(tenant, namespace, topic);
    }

    @GetMapping("/number")
    public PartitionNumberResponseDto getNumberOfPartitionsOfTopic(@RequestParam String tenant, @RequestParam String namespace, @RequestParam String topic) {
        //public and default
        return this.pulsarAdminPartitionService.getNumberOfPartitionsOfTopic(tenant, namespace, topic);
    }

}
