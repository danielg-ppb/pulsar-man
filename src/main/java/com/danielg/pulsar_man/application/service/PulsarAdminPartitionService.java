package com.danielg.pulsar_man.application.service;

import com.danielg.pulsar_man.application.port.input.partition.GetNumberOfPartitionsFromTopicUseCase;
import com.danielg.pulsar_man.application.port.input.partition.ListPartitionsFromTopicUseCase;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.response.PartitionListResponse;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.response.PartitionNumberResponse;
import com.danielg.pulsar_man.state.PulsarAdminManager;
import org.apache.pulsar.common.partition.PartitionedTopicMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PulsarAdminPartitionService implements GetNumberOfPartitionsFromTopicUseCase, ListPartitionsFromTopicUseCase {
    private static final Logger logger = LoggerFactory.getLogger(PulsarAdminPartitionService.class);

    private PulsarAdminManager pulsarAdminManager;

    public PulsarAdminPartitionService(PulsarAdminManager pulsarAdminManager) {
        this.pulsarAdminManager = pulsarAdminManager;
    }

    @Override
    public PartitionListResponse listPartitions(String tenant, String namespace, String topic) {
        String fullTopicName = "persistent://" + tenant + "/" + namespace + "/" + topic;
        try {
            PartitionedTopicMetadata metadata = pulsarAdminManager.getPulsarAdmin().topics().getPartitionedTopicMetadata(fullTopicName);

            logger.info(metadata.toString());
            System.out.println(metadata.partitions);
            return new PartitionListResponse(pulsarAdminManager.getPulsarAdmin().topics().getPartitionedTopicList(fullTopicName));
        } catch (Exception e) {
            logger.error("Failed to list partitions", e);
            throw new RuntimeException("Failed to list partitions", e);
        }
    }

    @Override
    public PartitionNumberResponse getNumberOfPartitionsOfTopic(String tenant, String namespace, String topic) {
        String fullTopicName = "persistent://" + tenant + "/" + namespace + "/" + topic;
        try {
            PartitionedTopicMetadata metadata = pulsarAdminManager.getPulsarAdmin().topics().getPartitionedTopicMetadata(fullTopicName);

            return new PartitionNumberResponse(metadata.partitions);
        } catch (Exception e) {
            logger.error("Failed to list partitions", e);
            throw new RuntimeException("Failed to list partitions", e);
        }
    }


}
