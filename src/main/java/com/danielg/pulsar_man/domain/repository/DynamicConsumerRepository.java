package com.danielg.pulsar_man.domain.repository;

import com.danielg.pulsar_man.domain.model.PulsarDynamicConsumer;

public interface DynamicConsumerRepository {
    void saveState(String key, PulsarDynamicConsumer consumer);

    PulsarDynamicConsumer getState(String key);

    PulsarDynamicConsumer getLatestState();

    void deleteState(String key);
}
