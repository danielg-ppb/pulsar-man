package com.danielg.pulsar_man.controller.admin;

import com.danielg.pulsar_man.dto.request.PulsarServiceUrlRequestDto;
import com.danielg.pulsar_man.dto.response.PartitionListResponseDto;
import com.danielg.pulsar_man.dto.response.TopicListResponseDto;
import com.danielg.pulsar_man.service.admin.PulsarAdminConnectionService;
import com.danielg.pulsar_man.service.admin.PulsarAdminTopicService;
import org.apache.pulsar.common.policies.data.TopicStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/pulsar-admin/topic")
public class PulsarAdminTopicController {
    private final PulsarAdminTopicService pulsarAdminTopicService;

    @Autowired
    public PulsarAdminTopicController(PulsarAdminTopicService pulsarAdminTopicService) {
        this.pulsarAdminTopicService = pulsarAdminTopicService;
    }

    @GetMapping
    public TopicListResponseDto listTopics(@RequestParam String tenant, @RequestParam String namespace) {
        //public and default
        return this.pulsarAdminTopicService.listTopics(tenant, namespace);
    }

    @GetMapping("/stats")
    public List<TopicStats> getPartitionedTopicStats(@RequestParam String tenant, @RequestParam String namespace, @RequestParam String topic) {
        //public and default
        return this.pulsarAdminTopicService.getPartitionedTopicStats(tenant, namespace, topic);
    }

}
