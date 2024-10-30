package com.danielg.pulsar_man.application.port.input.topic;

public interface DeleteNonPartitionedTopicUseCase {
    void deleteNonPartitionedTopic(String tenant, String namespace, String topic);
}
