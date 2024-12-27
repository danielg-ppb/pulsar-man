package com.danielg.pulsar_man.application.service;

import com.danielg.pulsar_man.application.port.input.subscription.BatchDeleteSubscriptionsUseCase;
import com.danielg.pulsar_man.application.port.input.subscription.DeleteSubscriptionUseCase;
import com.danielg.pulsar_man.application.port.input.subscription.ListSubscriptionsUseCase;
import com.danielg.pulsar_man.infrastructure.pulsar.factory.AdminFactory;
import com.danielg.pulsar_man.utils.PulsarTopicUtils;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService implements ListSubscriptionsUseCase, DeleteSubscriptionUseCase, BatchDeleteSubscriptionsUseCase {
    private static final Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

    private AdminFactory pulsarAdminFactory;

    public SubscriptionService(AdminFactory pulsarAdminFactory) {
        this.pulsarAdminFactory = pulsarAdminFactory;
    }

    //Same as consumer groups in kafka
    @Override
    public List<String> listSubscriptions(String tenant, String namespace, String topic) {
        try {
            String fullTopicName = PulsarTopicUtils.concatFullTopic(tenant, namespace, topic);
            return pulsarAdminFactory.getPulsarAdmin().topics().getSubscriptions(fullTopicName);
        } catch (PulsarAdminException e) {
            logger.error("Failed to list subscriptions", e);
            throw new RuntimeException("Failed to list consumer groups (subscriptions) for topic: " + topic, e);
        }
    }

    @Override
    public void deleteSubscription(String tenant, String namespace, String topic, String subscriptionName) {
        String fullTopicName = PulsarTopicUtils.concatFullTopic(tenant, namespace, topic);
        try {
            pulsarAdminFactory.getPulsarAdmin().topics().deleteSubscription(fullTopicName, subscriptionName);
            logger.info("Subscription '{}' deleted successfully for topic '{}'.", subscriptionName, fullTopicName);
        } catch (PulsarAdminException e) {
            logger.error("Failed to delete subscription: {} for topic: {}", subscriptionName, fullTopicName, e);
            throw new RuntimeException("Failed to delete subscription: " + subscriptionName, e);
        }
    }

    public void batchDeleteSubscription(String tenant, String namespace, String topic, List<String> subscriptionNames) {
        String fullTopicName = PulsarTopicUtils.concatFullTopic(tenant, namespace, topic);
        for (String subscriptionName : subscriptionNames) {
            this.deleteSubscription(tenant, namespace, topic, subscriptionName);
            logger.info("Subscription '{}' deleted successfully for topic '{}'.", subscriptionName, fullTopicName);
        }
    }
}
