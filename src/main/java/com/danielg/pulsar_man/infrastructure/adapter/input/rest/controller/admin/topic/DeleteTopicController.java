package com.danielg.pulsar_man.infrastructure.adapter.input.rest.controller.admin.topic;

import com.danielg.pulsar_man.application.port.input.topic.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/pulsar-admin/topic")
public class DeleteTopicController {
    private final DeleteNonPartitionedTopicUseCase deleteNonPartitionedTopicUseCase;
    private final DeletePartitionedTopicUseCase deletePartitionedTopicUseCase;

    @Autowired
    public DeleteTopicController(DeleteNonPartitionedTopicUseCase deleteNonPartitionedTopicUseCase, DeletePartitionedTopicUseCase deletePartitionedTopicUseCase) {
        this.deleteNonPartitionedTopicUseCase = deleteNonPartitionedTopicUseCase;
        this.deletePartitionedTopicUseCase = deletePartitionedTopicUseCase;
    }

    /**
     * WATCH OUT!!!!!
     *
     * @param tenant
     * @param namespace
     * @param topic
     * @return
     */
    @DeleteMapping("/non-partitioned")
    public String deleteNonPartitionedTopic(@RequestParam String tenant, @RequestParam String namespace, @RequestParam String topic) {
        //public and default
        this.deleteNonPartitionedTopicUseCase.deleteNonPartitionedTopic(tenant, namespace, topic);
        return "Non partitioned topic deleted successfully.";
    }

    /**
     * WATCH OUT!!!!!
     *
     * @param tenant
     * @param namespace
     * @param topic
     * @return
     */
    @DeleteMapping("/partitioned")
    public String deletePartitionedTopic(@RequestParam String tenant, @RequestParam String namespace, @RequestParam String topic) {
        //public and default
        this.deletePartitionedTopicUseCase.deletePartitionedTopic(tenant, namespace, topic);
        return "Partitioned topic deleted successfully.";
    }

}
