package com.danielg.pulsar_man.infrastructure.adapter.input.rest.controller.admin.topic;

import com.danielg.pulsar_man.application.port.input.topic.CreateNonPartitionedTopicUseCase;
import com.danielg.pulsar_man.application.port.input.topic.CreatePartitionedTopicUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/pulsar-admin/topic")
public class CreateTopicController {
    private final CreatePartitionedTopicUseCase createPartitionedTopicUseCase;
    private final CreateNonPartitionedTopicUseCase createNonPartitionedTopicUseCase;

    @Autowired
    public CreateTopicController(CreatePartitionedTopicUseCase createPartitionedTopicUseCase, CreateNonPartitionedTopicUseCase createNonPartitionedTopicUseCase) {
        this.createPartitionedTopicUseCase = createPartitionedTopicUseCase;
        this.createNonPartitionedTopicUseCase = createNonPartitionedTopicUseCase;
    }

    @PostMapping("/non-partitioned")
    public String createNonPartitionedTopic(@RequestParam String tenant, @RequestParam String namespace, @RequestParam String topic) {
        //public and default
        this.createNonPartitionedTopicUseCase.createNonPartitionedTopic(tenant, namespace, topic);
        return "Partitioned topic created successfully.";
    }

    @PostMapping("/partitioned")
    public String createPartitionedTopic(@RequestParam String tenant, @RequestParam String namespace, @RequestParam String topic, @RequestParam int partitions) {
        //public and default
        this.createPartitionedTopicUseCase.createPartitionedTopic(tenant, namespace, topic, partitions);
        return "Non Partitioned topic created successfully.";
    }
}
