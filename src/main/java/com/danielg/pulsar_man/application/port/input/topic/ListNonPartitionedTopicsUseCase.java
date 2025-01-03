package com.danielg.pulsar_man.application.port.input.topic;

import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.response.TopicListResponse;

public interface ListNonPartitionedTopicsUseCase {
    TopicListResponse listNonPartitionedTopics(String tenant, String namespace);
}
