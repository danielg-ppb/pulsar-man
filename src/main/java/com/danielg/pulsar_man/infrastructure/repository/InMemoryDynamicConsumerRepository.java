package com.danielg.pulsar_man.infrastructure.repository;

import com.danielg.pulsar_man.domain.model.PulsarDynamicConsumer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryDynamicConsumerRepository {
    private final Map<String, PulsarDynamicConsumer> stateMap = new ConcurrentHashMap<>();

    public void saveState(String key, PulsarDynamicConsumer state) {
        stateMap.put(key, state);
    }

    public PulsarDynamicConsumer getState(String key) {
        return stateMap.get(key);
    }

    public void deleteState(String key) {
        stateMap.remove(key);
    }
}
