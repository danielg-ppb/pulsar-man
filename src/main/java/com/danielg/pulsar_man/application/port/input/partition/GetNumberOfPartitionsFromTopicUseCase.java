package com.danielg.pulsar_man.application.port.input.partition;

import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.response.PartitionNumberResponse;

public interface GetNumberOfPartitionsFromTopicUseCase {
    public PartitionNumberResponse getNumberOfPartitionsOfTopic(String tenant, String namespace, String topic);
}
