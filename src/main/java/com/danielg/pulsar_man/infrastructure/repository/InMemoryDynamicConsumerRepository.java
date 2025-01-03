package com.danielg.pulsar_man.infrastructure.repository;

import com.danielg.pulsar_man.domain.model.PulsarDynamicConsumer;
import com.danielg.pulsar_man.domain.repository.DynamicConsumerRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryDynamicConsumerRepository implements DynamicConsumerRepository {
    private final Map<String, PulsarDynamicConsumer> stateMap = new ConcurrentHashMap<>();
    private volatile String latestKey;

    @Override
    public void saveState(String key, PulsarDynamicConsumer state) {
        stateMap.put(key, state);
        latestKey = key;
    }

    @Override
    public PulsarDynamicConsumer getState(String key) {
        return stateMap.getOrDefault(key, null);
    }

    @Override
    public PulsarDynamicConsumer getLatestState() {
        return stateMap.getOrDefault(latestKey, null);
    }

    @Override
    public void deleteState(String key) {
        stateMap.remove(key);
    }
}
