package com.danielg.pulsar_man.application.port.input.topic;

public interface CreateNonPartitionedTopicUseCase {
    void createNonPartitionedTopic(String tenant, String namespace, String topic);
}
