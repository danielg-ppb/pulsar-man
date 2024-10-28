package com.danielg.pulsar_man.service.admin;

import com.danielg.pulsar_man.dto.response.PartitionListResponseDto;
import com.danielg.pulsar_man.dto.response.PartitionNumberResponseDto;

public interface PulsarAdminPartitionService {
    public PartitionListResponseDto listPartitions(String tenant, String namespace, String topic);

    public PartitionNumberResponseDto getNumberOfPartitionsOfTopic(String tenant, String namespace, String topic);
}
