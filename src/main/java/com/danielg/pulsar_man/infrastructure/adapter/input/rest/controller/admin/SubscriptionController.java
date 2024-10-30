package com.danielg.pulsar_man.infrastructure.adapter.input.rest.controller.admin;

import com.danielg.pulsar_man.application.port.input.subscription.BatchDeleteSubscriptionsUseCase;
import com.danielg.pulsar_man.application.port.input.subscription.DeleteSubscriptionUseCase;
import com.danielg.pulsar_man.application.port.input.subscription.ListSubscriptionsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/pulsar-admin/subscription")
public class SubscriptionController {
    private final ListSubscriptionsUseCase listSubscriptionsUseCase;
    private final DeleteSubscriptionUseCase deleteSubscriptionUseCase;
    private final BatchDeleteSubscriptionsUseCase batchDeleteSubscriptionsUseCase;

    @Autowired
    public SubscriptionController(ListSubscriptionsUseCase listSubscriptionsUseCase, DeleteSubscriptionUseCase deleteSubscriptionUseCase, BatchDeleteSubscriptionsUseCase batchDeleteSubscriptionsUseCase) {
        this.listSubscriptionsUseCase = listSubscriptionsUseCase;
        this.deleteSubscriptionUseCase = deleteSubscriptionUseCase;
        this.batchDeleteSubscriptionsUseCase = batchDeleteSubscriptionsUseCase;
    }

    @GetMapping
    public List<String> listSubscriptions(@RequestParam String tenant, @RequestParam String namespace, @RequestParam String topic) {
        //public and default
        return this.listSubscriptionsUseCase.listSubscriptions(tenant, namespace, topic);
    }

    @DeleteMapping
    public String deleteSubscription(
            @RequestParam String tenant,
            @RequestParam String namespace,
            @RequestParam String topic,
            @RequestParam String subscriptionName) {

        this.deleteSubscriptionUseCase.deleteSubscription(tenant, namespace, topic, subscriptionName);
        return "Subscription deleted successfully.";
    }

    @DeleteMapping("/batch")
    public String batchDeleteSubscription(
            @RequestParam String tenant,
            @RequestParam String namespace,
            @RequestParam String topic,
            @RequestParam List<String> subscriptionNames) {

        this.batchDeleteSubscriptionsUseCase.batchDeleteSubscription(tenant, namespace, topic, subscriptionNames);
        return "Subscriptions deleted successfully.";
    }


}
