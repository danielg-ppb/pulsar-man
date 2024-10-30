package com.danielg.pulsar_man.application.port.input.subscription;

import java.util.List;

public interface ListSubscriptionsUseCase {
    public List<String> listSubscriptions(String tenant, String namespace, String topic);
}
