package com.danielg.pulsar_man.infrastructure.adapter.input.rest.controller.admin;

import com.danielg.pulsar_man.application.port.input.topic.*;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.response.TopicListResponse;
import org.apache.pulsar.common.policies.data.TopicStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/pulsar-admin/topic")
public class TopicController {
    private final GetPartitionedTopicStats partitionedTopicStats;
    private final ListTopicsUseCase listTopicsUseCase;
    private final CreatePartitionedTopicUseCase createPartitionedTopicUseCase;
    private final CreateNonPartitionedTopicUseCase createNonPartitionedTopicUseCase;
    private final ListNonPartitionedTopicsUseCase listNonPartitionedTopicsUseCase;
    private final ListPartitionedTopicsUseCase listPartitionedTopicsUseCase;

    @Autowired
    public TopicController(GetPartitionedTopicStats partitionedTopicStats, ListTopicsUseCase listTopicsUseCase,
                           CreatePartitionedTopicUseCase createPartitionedTopicUseCase, CreateNonPartitionedTopicUseCase createNonPartitionedTopicUseCase,
                           ListNonPartitionedTopicsUseCase listNonPartitionedTopicsUseCase, ListPartitionedTopicsUseCase listPartitionedTopicsUseCase) {
        this.partitionedTopicStats = partitionedTopicStats;
        this.listTopicsUseCase = listTopicsUseCase;
        this.createPartitionedTopicUseCase = createPartitionedTopicUseCase;
        this.createNonPartitionedTopicUseCase = createNonPartitionedTopicUseCase;
        this.listNonPartitionedTopicsUseCase = listNonPartitionedTopicsUseCase;
        this.listPartitionedTopicsUseCase = listPartitionedTopicsUseCase;
    }

    @GetMapping
    public TopicListResponse listTopics(@RequestParam String tenant, @RequestParam String namespace) {
        //public and default
        return this.listTopicsUseCase.listTopics(tenant, namespace);
    }

    @GetMapping("/non-partitioned")
    public TopicListResponse listTopicsNonPartitioned(@RequestParam String tenant, @RequestParam String namespace) {
        //public and default
        return this.listNonPartitionedTopicsUseCase.listNonPartitionedTopics(tenant, namespace);
    }

    @GetMapping("/partitioned")
    public TopicListResponse listTopicsPartitioned(@RequestParam String tenant, @RequestParam String namespace) {
        //public and default
        return this.listPartitionedTopicsUseCase.listPartitionedTopics(tenant, namespace);
    }

    @GetMapping("/stats")
    public List<TopicStats> getPartitionedTopicStats(@RequestParam String tenant, @RequestParam String namespace, @RequestParam String topic) {
        //public and default
        return this.partitionedTopicStats.getPartitionedTopicStats(tenant, namespace, topic);
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
