package com.danielg.pulsar_man.infrastructure.adapter.input.rest.controller.admin;

import com.danielg.pulsar_man.application.port.input.partition.GetNumberOfPartitionsFromTopicUseCase;
import com.danielg.pulsar_man.application.port.input.partition.ListPartitionsFromTopicUseCase;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.response.PartitionListResponse;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.response.PartitionNumberResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/pulsar-admin/partition")
public class PulsarAdminPartitionController {
    private final GetNumberOfPartitionsFromTopicUseCase getNumberOfPartitionsFromTopicUseCase;
    private final ListPartitionsFromTopicUseCase listPartitionsFromTopicUseCase;

    @Autowired
    public PulsarAdminPartitionController(GetNumberOfPartitionsFromTopicUseCase getNumberOfPartitionsFromTopicUseCase, ListPartitionsFromTopicUseCase listPartitionsFromTopicUseCase) {
        this.getNumberOfPartitionsFromTopicUseCase = getNumberOfPartitionsFromTopicUseCase;
        this.listPartitionsFromTopicUseCase = listPartitionsFromTopicUseCase;
    }

    @GetMapping
    public PartitionListResponse listPartitions(@RequestParam String tenant, @RequestParam String namespace, @RequestParam String topic) {
        //public and default
        return this.listPartitionsFromTopicUseCase.listPartitions(tenant, namespace, topic);
    }

    @GetMapping("/number")
    public PartitionNumberResponse getNumberOfPartitionsOfTopic(@RequestParam String tenant, @RequestParam String namespace, @RequestParam String topic) {
        //public and default
        return this.getNumberOfPartitionsFromTopicUseCase.getNumberOfPartitionsOfTopic(tenant, namespace, topic);
    }

}
