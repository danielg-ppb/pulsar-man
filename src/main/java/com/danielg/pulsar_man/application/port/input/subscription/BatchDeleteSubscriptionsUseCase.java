package com.danielg.pulsar_man.application.port.input.subscription;

import java.util.List;

public interface BatchDeleteSubscriptionsUseCase {
    public void batchDeleteSubscription(String tenant, String namespace, String topic, List<String> subscriptionNames);

}
