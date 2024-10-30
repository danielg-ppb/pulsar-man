package com.danielg.pulsar_man.application.port.input.topic;

public interface CreatePartitionedTopicUseCase {
    void createPartitionedTopic(String tenant, String namespace, String topic, int partitions);
}
