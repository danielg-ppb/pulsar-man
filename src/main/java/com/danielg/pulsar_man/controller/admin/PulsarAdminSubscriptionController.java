package com.danielg.pulsar_man.controller.admin;

import com.danielg.pulsar_man.dto.response.TopicListResponseDto;
import com.danielg.pulsar_man.service.admin.PulsarAdminPartitionService;
import com.danielg.pulsar_man.service.admin.PulsarAdminSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/pulsar-admin/subscription")
public class PulsarAdminSubscriptionController {
    private final PulsarAdminSubscriptionService pulsarAdminSubscriptionService;

    @Autowired
    public PulsarAdminSubscriptionController(PulsarAdminSubscriptionService pulsarAdminSubscriptionService) {
        this.pulsarAdminSubscriptionService = pulsarAdminSubscriptionService;
    }

    @GetMapping
    public List<String> listSubscriptions(@RequestParam String tenant, @RequestParam String namespace, @RequestParam String topic) {
        //public and default
        return this.pulsarAdminSubscriptionService.listSubscriptions(tenant, namespace, topic);
    }

    @DeleteMapping
    public String deleteSubscription(
            @RequestParam String tenant,
            @RequestParam String namespace,
            @RequestParam String topic,
            @RequestParam String subscriptionName) {

        this.pulsarAdminSubscriptionService.deleteSubscription(tenant, namespace, topic, subscriptionName);
        return "Subscription deleted successfully.";
    }

    @DeleteMapping("/batch")
    public String batchDeleteSubscription(
            @RequestParam String tenant,
            @RequestParam String namespace,
            @RequestParam String topic,
            @RequestParam List<String> subscriptionNames) {

        this.pulsarAdminSubscriptionService.batchDeleteSubscription(tenant, namespace, topic, subscriptionNames);
        return "Subscriptions deleted successfully.";
    }


}
