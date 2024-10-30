package com.danielg.pulsar_man.application.port.input.subscription;

import java.util.List;

public interface ListSubscriptionsUseCase {
    List<String> listSubscriptions(String tenant, String namespace, String topic);
}
