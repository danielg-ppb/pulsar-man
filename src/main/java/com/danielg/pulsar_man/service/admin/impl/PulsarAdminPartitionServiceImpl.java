package com.danielg.pulsar_man.service.admin.impl;

import com.danielg.pulsar_man.dto.response.PartitionListResponseDto;
import com.danielg.pulsar_man.dto.response.PartitionNumberResponseDto;
import com.danielg.pulsar_man.service.admin.PulsarAdminPartitionService;
import com.danielg.pulsar_man.state.PulsarAdminManager;
import org.apache.pulsar.common.partition.PartitionedTopicMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PulsarAdminPartitionServiceImpl implements PulsarAdminPartitionService {
    private static final Logger logger = LoggerFactory.getLogger(PulsarAdminPartitionServiceImpl.class);

    private PulsarAdminManager pulsarAdminManager;

    public PulsarAdminPartitionServiceImpl(PulsarAdminManager pulsarAdminManager) {
        this.pulsarAdminManager = pulsarAdminManager;
    }

    @Override
    public PartitionListResponseDto listPartitions(String tenant, String namespace, String topic) {
        String fullTopicName = "persistent://" + tenant + "/" + namespace + "/" + topic;
        try {
            PartitionedTopicMetadata metadata = pulsarAdminManager.getPulsarAdmin().topics().getPartitionedTopicMetadata(fullTopicName);

            logger.info(metadata.toString());
            System.out.println(metadata.partitions);
            return new PartitionListResponseDto(pulsarAdminManager.getPulsarAdmin().topics().getPartitionedTopicList(fullTopicName));
        } catch (Exception e) {
            logger.error("Failed to list partitions", e);
            throw new RuntimeException("Failed to list partitions", e);
        }
    }

    @Override
    public PartitionNumberResponseDto getNumberOfPartitionsOfTopic(String tenant, String namespace, String topic) {
        String fullTopicName = "persistent://" + tenant + "/" + namespace + "/" + topic;
        try {
            PartitionedTopicMetadata metadata = pulsarAdminManager.getPulsarAdmin().topics().getPartitionedTopicMetadata(fullTopicName);

            return new PartitionNumberResponseDto(metadata.partitions);
        } catch (Exception e) {
            logger.error("Failed to list partitions", e);
            throw new RuntimeException("Failed to list partitions", e);
        }
    }


}
