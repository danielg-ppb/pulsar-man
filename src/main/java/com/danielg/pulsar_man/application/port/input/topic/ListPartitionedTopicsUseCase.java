package com.danielg.pulsar_man.application.port.input.topic;

import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.response.TopicListResponse;

public interface ListPartitionedTopicsUseCase {
    TopicListResponse listPartitionedTopics(String tenant, String namespace);
}
