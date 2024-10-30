package com.danielg.pulsar_man.infrastructure.adapter.input.rest.controller.admin;

import com.danielg.pulsar_man.application.port.input.topic.GetPartitionedTopicStats;
import com.danielg.pulsar_man.application.port.input.topic.ListTopicsUseCase;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.response.TopicListResponse;
import org.apache.pulsar.common.policies.data.TopicStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/pulsar-admin/topic")
public class PulsarAdminTopicController {
    private final GetPartitionedTopicStats partitionedTopicStats;
    private final ListTopicsUseCase listTopicsUseCase;

    @Autowired
    public PulsarAdminTopicController(GetPartitionedTopicStats partitionedTopicStats, ListTopicsUseCase listTopicsUseCase) {
        this.partitionedTopicStats = partitionedTopicStats;
        this.listTopicsUseCase = listTopicsUseCase;
    }

    @GetMapping
    public TopicListResponse listTopics(@RequestParam String tenant, @RequestParam String namespace) {
        //public and default
        return this.listTopicsUseCase.listTopics(tenant, namespace);
    }

    @GetMapping("/stats")
    public List<TopicStats> getPartitionedTopicStats(@RequestParam String tenant, @RequestParam String namespace, @RequestParam String topic) {
        //public and default
        return this.partitionedTopicStats.getPartitionedTopicStats(tenant, namespace, topic);
    }

}
