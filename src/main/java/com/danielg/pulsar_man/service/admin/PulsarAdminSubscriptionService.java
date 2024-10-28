package com.danielg.pulsar_man.service.admin;

import java.util.List;

public interface PulsarAdminSubscriptionService {
    public List<String> listSubscriptions(String tenant, String namespace, String topic);
    public void deleteSubscription(String tenant, String namespace, String topic, String subscriptionName);
    public void batchDeleteSubscription(String tenant, String namespace, String topic, List<String> subscriptionNames);
}
