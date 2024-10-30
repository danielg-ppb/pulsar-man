package com.danielg.pulsar_man.application.port.input.subscription;

public interface DeleteSubscriptionUseCase {
    void deleteSubscription(String tenant, String namespace, String topic, String subscription);
}
