package com.danielg.pulsar_man.application.port.input.partition;

import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.response.PartitionListResponse;

public interface ListPartitionsFromTopicUseCase {
    PartitionListResponse listPartitions(String tenant, String namespace, String topic);

}
