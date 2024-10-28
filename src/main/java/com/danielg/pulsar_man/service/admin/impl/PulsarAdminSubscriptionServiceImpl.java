package com.danielg.pulsar_man.service.admin.impl;

import com.danielg.pulsar_man.service.admin.PulsarAdminSubscriptionService;
import com.danielg.pulsar_man.state.PulsarAdminManager;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PulsarAdminSubscriptionServiceImpl implements PulsarAdminSubscriptionService {
    private static final Logger logger = LoggerFactory.getLogger(PulsarAdminSubscriptionServiceImpl.class);

    private PulsarAdminManager pulsarAdminManager;

    public PulsarAdminSubscriptionServiceImpl(PulsarAdminManager pulsarAdminManager) {
        this.pulsarAdminManager = pulsarAdminManager;
    }

    //Same as consumer groups in kafka
    @Override
    public List<String> listSubscriptions(String tenant, String namespace, String topic) {
        try {
            String fullTopicName = "persistent://" + tenant + "/" + namespace + "/" + topic;
            return pulsarAdminManager.getPulsarAdmin().topics().getSubscriptions(fullTopicName);
        } catch (PulsarAdminException e) {
            logger.error("Failed to list subscriptions", e);
            throw new RuntimeException("Failed to list consumer groups (subscriptions) for topic: " + topic, e);
        }
    }

    @Override
    public void deleteSubscription(String tenant, String namespace, String topic, String subscriptionName) {
        String fullTopicName = "persistent://" + tenant + "/" + namespace + "/" + topic;
        try {
            pulsarAdminManager.getPulsarAdmin().topics().deleteSubscription(fullTopicName, subscriptionName);
            logger.info("Subscription '{}' deleted successfully for topic '{}'.", subscriptionName, fullTopicName);
        } catch (PulsarAdminException e) {
            logger.error("Failed to delete subscription: {} for topic: {}", subscriptionName, fullTopicName, e);
            throw new RuntimeException("Failed to delete subscription: " + subscriptionName, e);
        }
    }

    public void batchDeleteSubscription(String tenant, String namespace, String topic, List<String> subscriptionNames) {
        String fullTopicName = "persistent://" + tenant + "/" + namespace + "/" + topic;
        for (String subscriptionName : subscriptionNames) {
            this.deleteSubscription(tenant, namespace, topic, subscriptionName);
            logger.info("Subscription '{}' deleted successfully for topic '{}'.", subscriptionName, fullTopicName);
        }
    }
}
