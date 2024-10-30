package com.danielg.pulsar_man.application.port.input.topic;

public interface DeletePartitionedTopicUseCase {
    void deletePartitionedTopic(String tenant, String namespace, String topic);
}
